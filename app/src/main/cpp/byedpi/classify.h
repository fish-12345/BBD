#ifndef CLASSIFY_H
#define CLASSIFY_H

#include <stddef.h>

/* Определение протокола по первым байтам полезной нагрузки.
   Вызывается один раз на соединение (или на первую датаграмму),
   поэтому проверки сделаны короткими: только сравнения заголовков. */

enum conn_kind {
    CONN_KIND_UNKNOWN = 0,
    CONN_KIND_TLS     = 1,
    CONN_KIND_HTTP    = 2,
    CONN_KIND_HTTP2   = 3,
    CONN_KIND_DNS     = 4,
    CONN_KIND_QUIC    = 5,
    CONN_KIND_DTLS    = 6,
    CONN_KIND_SSH     = 7,
    CONN_KIND_STUN    = 8
};

/* TCP-поток: TLS, HTTP/1.x, HTTP/2, SSH, DNS-over-TCP. */
int classify_stream(const char *data, size_t len);

/* UDP-датаграмма: DNS, QUIC, DTLS, STUN. */
int classify_datagram(const char *data, size_t len);

#endif
