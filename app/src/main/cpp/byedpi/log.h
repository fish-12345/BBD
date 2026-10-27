#ifndef BYEDPI_LOG_H
#define BYEDPI_LOG_H

#include <stddef.h>

void log_init(void);
void log_printf(const char *fmt, ...)
__attribute__((format(printf, 1, 2)));

/* Возвращает malloc-строку со всем накопленным логом и ОЧИЩАЕТ буфер.
   Освобождать через free(). Может вернуть NULL, если логов нет. */
char *log_take_str(void);

/* Возвращает malloc-копию последних max_len байт лога, НЕ очищая буфер.
   В *total_out записывается общее число записанных байт (растёт монотонно),
   чтобы на стороне Java можно было понять, сколько данных добавилось.
   Освобождать через free(). Может вернуть NULL, если логов нет. */
char *log_peek_str(size_t max_len, size_t *total_out);

#endif