#ifndef BYEDPI_LOG_H
#define BYEDPI_LOG_H

#include <stddef.h>

void log_init(void);
void log_printf(const char *fmt, ...)
__attribute__((format(printf, 1, 2)));

/* Возвращает malloc-строку со всем накопленным логом и ОЧИЩАЕТ буфер.
   Освобождать через free(). Может вернуть NULL, если логов нет. */
char *log_take_str(void);

#endif