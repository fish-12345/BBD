#ifndef CONN_STATS_H
#define CONN_STATS_H

#include <stdint.h>
#include <stddef.h>

/* Кольцевой буфер последних соединений. Отдельный от лога: UI читает его
   напрямую через JNI и не зависит от строковых сообщений. */

#define CONN_STATS_CAP      256
#define CONN_STATS_HOST_MAX 256
#define CONN_STATS_ADDR_MAX 64
#define CONN_STATS_ERR_MAX  48

enum conn_state {
    CONN_STATE_OPEN    = 0,
    CONN_STATE_DESYNC  = 1,
    CONN_STATE_CLOSED  = 2,
    CONN_STATE_TIMEOUT = 3,
    CONN_STATE_ERROR   = 4
};

enum conn_proto {
    CONN_PROTO_TCP = 0,
    CONN_PROTO_UDP = 1
};

enum conn_side {
    CONN_SIDE_NONE   = 0,
    CONN_SIDE_CLIENT = 1, /* сторона приложения: recv_count = отдано */
    CONN_SIDE_REMOTE = 2  /* сторона сети:       recv_count = скачано */
};

struct conn_stat {
    uint64_t  id;
    int       fd;
    int       peer_fd;
    int       state;
    int       proto;
    int       is_dns;
    int       rounds;
    long long up;
    long long down;
    long long start_ms;
    long long end_ms;
    /* внутреннее: когда последний раз обновляли байты из горячего пути */
    long long upd_ms;
    char      host[CONN_STATS_HOST_MAX];
    char      dst[CONN_STATS_ADDR_MAX];
    char      err[CONN_STATS_ERR_MAX];
};

void     conn_stats_reset(void);

uint64_t conn_stats_open(int fd, int peer_fd, int proto,
                         const char *host, int host_len, const char *dst);
void     conn_stats_host(uint64_t id, const char *host, int host_len);
void     conn_stats_desync(uint64_t id);
void     conn_stats_rounds(uint64_t id, int rounds);
/* -1 в аргументе — «не менять» */
void     conn_stats_bytes(uint64_t id, long long up, long long down);
/* Обновление счётчика из горячего пути: не чаще ~2 раз в секунду,
   чтобы UI видел байты ещё до закрытия соединения. side — CONN_SIDE_*. */
void     conn_stats_tick(uint64_t id, int side, long long value, int rounds);
void     conn_stats_error(uint64_t id, int state, const char *err);
void     conn_stats_close(uint64_t id, int state);
/* Неудачная попытка без соединения (например, домен не разрешился). */
void     conn_stats_failed(const char *host, int host_len, const char *err);

/* Снимок таблицы: первая строка — сколько всего соединений создано,
   дальше по строке на запись, поля через \t (см. разбор в Kotlin). */
char    *conn_stats_snapshot(size_t *out_len);

#endif
