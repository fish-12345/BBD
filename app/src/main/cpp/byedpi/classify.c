#include "classify.h"

#include <string.h>

static int has_prefix(const unsigned char *p, size_t len, const char *s)
{
    size_t n = strlen(s);
    return len >= n && memcmp(p, s, n) == 0;
}

/* DNS-сообщение (заголовок + вопрос). Отсекаем мусор по счётчикам
   и проверяем, что первое имя действительно читается до конца. */
static int is_dns(const unsigned char *p, size_t len)
{
    unsigned int qd, an, ns, ar;
    size_t off;

    if (len < 12) return 0;

    qd = ((unsigned int)p[4] << 8) | p[5];
    an = ((unsigned int)p[6] << 8) | p[7];
    ns = ((unsigned int)p[8] << 8) | p[9];
    ar = ((unsigned int)p[10] << 8) | p[11];

    if (qd > 8 || an > 64 || ns > 16 || ar > 16) return 0;
    if (!qd && !an) return 0;
    if (((p[2] >> 3) & 0x0f) > 5) return 0;  /* opcode */
    if ((p[3] & 0x0f) > 5) return 0;         /* rcode  */

    if (!qd) return 1;

    off = 12;
    for (;;) {
        unsigned int l;

        if (off >= len) return 0;
        l = p[off++];
        if (!l) break;
        if (l & 0xc0) return 0;      /* сжатие в вопросе недопустимо */
        if (off + l > len) return 0;
        off += l;
        if (off - 12 > 255) return 0;
    }
    /* qtype + qclass */
    return off + 4 <= len;
}

static int is_dns_stream(const unsigned char *p, size_t len)
{
    size_t msg;

    if (len < 14) return 0;
    msg = ((size_t)p[0] << 8) | p[1];
    return msg == len - 2 && is_dns(p + 2, msg);
}

int classify_stream(const char *data, size_t len)
{
    static const char *const methods[] = {
        "GET ", "POST ", "HEAD ", "PUT ", "DELETE ", "OPTIONS ",
        "PATCH ", "CONNECT ", "TRACE "
    };
    const unsigned char *p = (const unsigned char *)data;

    if (!p || !len) return CONN_KIND_UNKNOWN;

    /* TLS record: handshake 0x16, версия 3.x */
    if (p[0] == 0x16 && len >= 3 && p[1] == 0x03 && p[2] <= 0x04) {
        return CONN_KIND_TLS;
    }

    if (has_prefix(p, len, "PRI * HTTP/2.0")) return CONN_KIND_HTTP2;

    for (size_t i = 0; i < sizeof(methods) / sizeof(methods[0]); i++) {
        if (has_prefix(p, len, methods[i])) return CONN_KIND_HTTP;
    }

    if (has_prefix(p, len, "HTTP/1.") || has_prefix(p, len, "HTTP/2")) {
        return CONN_KIND_HTTP;
    }

    if (has_prefix(p, len, "SSH-")) return CONN_KIND_SSH;

    if (is_dns_stream(p, len)) return CONN_KIND_DNS;

    return CONN_KIND_UNKNOWN;
}

int classify_datagram(const char *data, size_t len)
{
    const unsigned char *p = (const unsigned char *)data;
    unsigned int msg_len, version;

    if (!p || !len) return CONN_KIND_UNKNOWN;

    /* DNS чаще всего встречается в UDP, проверяем первым. */
    if (is_dns(p, len)) return CONN_KIND_DNS;

    /* QUIC long header: два старших бита 11, версия не нулевая. */
    if (len >= 6 && (p[0] & 0xc0) == 0xc0) {
        version = ((unsigned int)p[1] << 24) | ((unsigned int)p[2] << 16)
                | ((unsigned int)p[3] << 8) | p[4];
        if (version) return CONN_KIND_QUIC;
    }

    if (len >= 3 && p[0] == 0x16 && p[1] == 0xfe
        && (p[2] == 0xff || p[2] == 0xfd || p[2] == 0xfc)) {
        return CONN_KIND_DTLS;
    }

    /* STUN: старшие два бита нулевые, фиксированный magic cookie. */
    if (len >= 20 && (p[0] & 0xc0) == 0
        && p[4] == 0x21 && p[5] == 0x12 && p[6] == 0xa4 && p[7] == 0x42) {
        msg_len = ((unsigned int)p[2] << 8) | p[3];
        if ((msg_len & 3) == 0 && (size_t)msg_len + 20 <= len) {
            return CONN_KIND_STUN;
        }
    }

    return CONN_KIND_UNKNOWN;
}
