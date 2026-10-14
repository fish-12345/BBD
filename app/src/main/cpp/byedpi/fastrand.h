#ifndef FASTRAND_H
#define FASTRAND_H

#include <stdint.h>

/* xorshift64 — без блокировок, ~1 ns на вызов */
extern uint64_t g_fast_rand_state;

uint32_t fast_rand(void);
uint64_t fast_rand64(void);
void fast_rand_seed(uint64_t seed);

#endif