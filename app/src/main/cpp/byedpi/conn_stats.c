#include "conn_stats.h"

#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdarg.h>
#include <sys/time.h>

/* Таблица соединений для UI. Заполняется из горячего пути прокси,
   читается Java-потоком через conn_stats_snapshot(). Доступ защищён
   одним мьютексом: операции короткие (копирование нескольких чисел). */

static pthread_mutex_t g_lock = PTHREAD_MUTEX_INITIALIZER;
static struct conn_stat g_table[CONN_STATS_CAP];
static uint64_t g_last_id = 0;
static uint64_t g_total = 0;

static long long now_ms(void)
{
    struct timeval tv;
    gettimeofday(&tv, NULL);
    return (long long)tv.tv_sec * 1000 + tv.tv_usec / 1000;
}

static void copy_str(char *dst, size_t cap, const char *src, int len)
{
    size_t k = 0;

    if (!dst || !cap) return;
    if (!src) { dst[0] = 0; return; }
    if (len < 0) len = (int)strlen(src);
    if ((size_t)len >= cap) len = (int)cap - 1;

    for (int i = 0; i < len; i++) {
        unsigned char c = (unsigned char)src[i];
        dst[k++] = (c < 0x20 || c == 0x7f) ? ' ' : (char)c;
    }
    dst[k] = 0;
}

static int is_dns_addr(const char *dst)
{
    size_t len;

    if (!dst) return 0;
    len = strlen(dst);
    return len > 3 && strcmp(dst + len - 3, ":53") == 0;
}

static struct conn_stat *find_locked(uint64_t id)
{
    struct conn_stat *st;

    if (!id) return NULL;
    st = &g_table[id % CONN_STATS_CAP];
    return st->id == id ? st : NULL;
}

void conn_stats_reset(void)
{
    pthread_mutex_lock(&g_lock);
    memset(g_table, 0, sizeof(g_table));
    /* g_last_id не сбрасываем: старые struct eval не должны попасть
       в чужую запись после перезапуска прокси. */
    g_total = 0;
    pthread_mutex_unlock(&g_lock);
}

uint64_t conn_stats_open(int fd, int peer_fd, int proto,
                         const char *host, int host_len, const char *dst)
{
    uint64_t id;
    struct conn_stat *st;

    pthread_mutex_lock(&g_lock);

    id = ++g_last_id;
    st = &g_table[id % CONN_STATS_CAP];
    memset(st, 0, sizeof(*st));

    st->id = id;
    st->fd = fd;
    st->peer_fd = peer_fd;
    st->state = CONN_STATE_OPEN;
    st->proto = proto;
    st->is_dns = is_dns_addr(dst);
    st->start_ms = now_ms();
    copy_str(st->host, sizeof(st->host), host, host_len);
    copy_str(st->dst, sizeof(st->dst), dst, -1);

    g_total++;

    pthread_mutex_unlock(&g_lock);
    return id;
}

void conn_stats_host(uint64_t id, const char *host, int host_len)
{
    struct conn_stat *st;

    if (!id || !host || host_len <= 0) return;

    pthread_mutex_lock(&g_lock);
    st = find_locked(id);
    if (st) copy_str(st->host, sizeof(st->host), host, host_len);
    pthread_mutex_unlock(&g_lock);
}

void conn_stats_desync(uint64_t id)
{
    struct conn_stat *st;

    pthread_mutex_lock(&g_lock);
    st = find_locked(id);
    if (st && st->state == CONN_STATE_OPEN) st->state = CONN_STATE_DESYNC;
    pthread_mutex_unlock(&g_lock);
}

void conn_stats_rounds(uint64_t id, int rounds)
{
    struct conn_stat *st;

    pthread_mutex_lock(&g_lock);
    st = find_locked(id);
    if (st && rounds > st->rounds) st->rounds = rounds;
    pthread_mutex_unlock(&g_lock);
}

void conn_stats_bytes(uint64_t id, long long up, long long down)
{
    struct conn_stat *st;

    pthread_mutex_lock(&g_lock);
    st = find_locked(id);
    if (st) {
        if (up >= 0) st->up = up;
        if (down >= 0) st->down = down;
    }
    pthread_mutex_unlock(&g_lock);
}

void conn_stats_tick(uint64_t id, int side, long long value, int rounds)
{
    struct conn_stat *st;
    long long now;

    if (!id || value < 0) return;

    pthread_mutex_lock(&g_lock);
    st = find_locked(id);
    if (st) {
        now = now_ms();
        if (now - st->upd_ms >= 500) {
            st->upd_ms = now;
            if (side == CONN_SIDE_CLIENT) {
                st->up = value;
                if (rounds > st->rounds) st->rounds = rounds;
            } else {
                st->down = value;
            }
        }
    }
    pthread_mutex_unlock(&g_lock);
}

void conn_stats_error(uint64_t id, int state, const char *err)
{
    struct conn_stat *st;

    pthread_mutex_lock(&g_lock);
    st = find_locked(id);
    if (st) {
        st->state = state;
        if (!st->end_ms) st->end_ms = now_ms();
        copy_str(st->err, sizeof(st->err), err, -1);
    }
    pthread_mutex_unlock(&g_lock);
}

void conn_stats_close(uint64_t id, int state)
{
    struct conn_stat *st;

    pthread_mutex_lock(&g_lock);
    st = find_locked(id);
    if (st) {
        if (st->state != CONN_STATE_ERROR && st->state != CONN_STATE_TIMEOUT) {
            st->state = state;
        }
        if (!st->end_ms) st->end_ms = now_ms();
    }
    pthread_mutex_unlock(&g_lock);
}

void conn_stats_failed(const char *host, int host_len, const char *err)
{
    uint64_t id;
    struct conn_stat *st;
    long long ts;

    pthread_mutex_lock(&g_lock);

    id = ++g_last_id;
    st = &g_table[id % CONN_STATS_CAP];
    memset(st, 0, sizeof(*st));

    ts = now_ms();
    st->id = id;
    st->fd = -1;
    st->peer_fd = -1;
    st->state = CONN_STATE_ERROR;
    st->proto = CONN_PROTO_TCP;
    st->start_ms = ts;
    st->end_ms = ts;
    copy_str(st->host, sizeof(st->host), host, host_len);
    copy_str(st->err, sizeof(st->err), err, -1);

    g_total++;

    pthread_mutex_unlock(&g_lock);
}


struct sbuf {
    char *p;
    size_t len;
    size_t cap;
    int    failed;
};

static int sbuf_grow(struct sbuf *b, size_t extra)
{
    size_t need = b->len + extra + 1;
    size_t cap;
    char *p;

    if (b->failed) return -1;
    if (need <= b->cap) return 0;

    cap = b->cap ? b->cap : 4096;
    while (cap < need) cap *= 2;

    p = realloc(b->p, cap);
    if (!p) { b->failed = 1; return -1; }
    b->p = p;
    b->cap = cap;
    return 0;
}

static void sbuf_append(struct sbuf *b, const char *fmt, ...)
{
    va_list ap;
    va_list cp;
    int n;

    if (sbuf_grow(b, 512) < 0) return;

    va_start(ap, fmt);
    va_copy(cp, ap);
    n = vsnprintf(b->p + b->len, b->cap - b->len, fmt, ap);
    va_end(ap);

    if (n < 0) {
        b->failed = 1;
        va_end(cp);
        return;
    }
    if ((size_t)n >= b->cap - b->len) {
        if (sbuf_grow(b, (size_t)n + 1) < 0) {
            va_end(cp);
            return;
        }
        n = vsnprintf(b->p + b->len, b->cap - b->len, fmt, cp);
        if (n < 0) {
            b->failed = 1;
            va_end(cp);
            return;
        }
    }
    va_end(cp);
    b->len += (size_t)n;
}

char *conn_stats_snapshot(size_t *out_len)
{
    struct sbuf b = {0};
    uint64_t last;
    uint64_t first;

    pthread_mutex_lock(&g_lock);

    sbuf_append(&b, "%llu\n", (unsigned long long)g_total);

    last = g_last_id;
    first = last > CONN_STATS_CAP ? last - CONN_STATS_CAP + 1 : 1;

    for (uint64_t id = last; id >= first; id--) {
        struct conn_stat *st = &g_table[id % CONN_STATS_CAP];
        if (st->id != id) {
            if (id == first) break;
            continue;
        }

        sbuf_append(&b,
            "%llu\t%d\t%d\t%d\t%d\t%d\t%d\t%lld\t%lld\t%lld\t%lld\t%s\t%s\t%s\n",
            (unsigned long long)st->id,
            st->fd, st->peer_fd, st->state, st->proto, st->is_dns,
            st->rounds, st->up, st->down,
            st->start_ms, st->end_ms,
            st->host, st->dst, st->err);

        if (id == first) break;
    }

    pthread_mutex_unlock(&g_lock);

    if (b.failed) {
        free(b.p);
        return NULL;
    }
    if (!b.p) {
        b.p = malloc(1);
        if (!b.p) return NULL;
        b.p[0] = 0;
    }
    if (out_len) *out_len = b.len;
    return b.p;
}
