#include "log.h"

#include <stdio.h>
#include <stdlib.h>
#include <stdarg.h>
#include <string.h>
#include <time.h>
#include <sys/time.h>
#include <pthread.h>

static pthread_mutex_t g_lock = PTHREAD_MUTEX_INITIALIZER;
static char   *g_buf  = NULL;
static size_t  g_len  = 0;
static size_t  g_cap  = 0;
static size_t  g_total = 0; /* сколько байт всего записано, не уменьшается */

#define LOG_INITIAL_CAP (64 * 1024)
#define LOG_MAX_CAP     (2 * 1024 * 1024)

/* Возвращает строку вида "10-14 15:42:07.123 " (с пробелом в конце). */
static size_t format_timestamp(char *out, size_t out_sz)
{
    struct timeval tv;
    gettimeofday(&tv, NULL);

    struct tm tm;
    localtime_r(&tv.tv_sec, &tm);

    int n = snprintf(out, out_sz,
                     "%02d-%02d %02d:%02d:%02d.%03d ",
                     tm.tm_mon + 1, tm.tm_mday,
                     tm.tm_hour, tm.tm_min, tm.tm_sec,
                     (int)(tv.tv_usec / 1000));

    if (n < 0) {
        return 0;
    }
    if ((size_t)n >= out_sz) {
        return out_sz - 1;
    }
    return (size_t)n;
}

static void write_locked(const char *data, size_t n)
{
    if (!g_buf) {
        g_buf = malloc(LOG_INITIAL_CAP);
        if (g_buf) {
            g_cap = LOG_INITIAL_CAP;
            g_len = 0;
        }
    }
    if (g_buf && g_len + n > g_cap) {
        size_t new_cap = g_cap * 2;
        while (new_cap < g_len + n) new_cap *= 2;
        if (new_cap > LOG_MAX_CAP) new_cap = LOG_MAX_CAP;

        if (g_len + n > new_cap) {
            size_t drop = g_len + n - new_cap;
            memmove(g_buf, g_buf + drop, g_len - drop);
            g_len -= drop;
        }
        if (new_cap > g_cap) {
            char *nb = realloc(g_buf, new_cap);
            if (nb) {
                g_buf = nb;
                g_cap = new_cap;
            }
        }
    }
    if (g_buf && g_len + n <= g_cap) {
        memcpy(g_buf + g_len, data, n);
        g_len += n;
        g_total += n;
    }
}

void log_init(void)
{
    pthread_mutex_lock(&g_lock);
    if (!g_buf) {
        g_buf = malloc(LOG_INITIAL_CAP);
        if (g_buf) {
            g_cap = LOG_INITIAL_CAP;
            g_len = 0;
        }
    }
    pthread_mutex_unlock(&g_lock);
}

void log_printf(const char *fmt, ...)
{
    char stack[512];
    char ts[32];

    va_list ap;
    va_start(ap, fmt);
    int n = vsnprintf(stack, sizeof(stack), fmt, ap);
    va_end(ap);
    if (n < 0) return;

    size_t ts_len = format_timestamp(ts, sizeof(ts));

    if (n < (int)sizeof(stack)) {
        pthread_mutex_lock(&g_lock);
        write_locked(ts, ts_len);
        write_locked(stack, (size_t)n);
        pthread_mutex_unlock(&g_lock);
        return;
    }

    char *heap = malloc((size_t)n + 1);
    if (!heap) return;
    va_start(ap, fmt);
    vsnprintf(heap, (size_t)n + 1, fmt, ap);
    va_end(ap);

    pthread_mutex_lock(&g_lock);
    write_locked(ts, ts_len);
    write_locked(heap, (size_t)n);
    pthread_mutex_unlock(&g_lock);
    free(heap);
}

char *log_take_str(void)
{
    pthread_mutex_lock(&g_lock);
    char *s = NULL;
    if (g_len) {
        s = malloc(g_len + 1);
        if (s) {
            memcpy(s, g_buf, g_len);
            s[g_len] = 0;
        }
        g_len = 0;
    }
    pthread_mutex_unlock(&g_lock);
    return s;
}

char *log_peek_str(size_t max_len, size_t *total_out)
{
    pthread_mutex_lock(&g_lock);
    if (total_out) {
        *total_out = g_total;
    }

    size_t len = g_len;
    if (len > max_len) {
        len = max_len;
    }

    char *s = NULL;
    if (len) {
        s = malloc(len + 1);
        if (s) {
            memcpy(s, g_buf + (g_len - len), len);
            s[len] = 0;
        }
    }
    pthread_mutex_unlock(&g_lock);
    return s;
}