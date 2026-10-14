#include "fastrand.h"

uint64_t g_fast_rand_state = 0x9e3779b97f4a7c15ULL;

uint32_t fast_rand(void)
{
    uint64_t s = g_fast_rand_state;
    s ^= s << 13;
    s ^= s >> 7;
    s ^= s << 17;
    g_fast_rand_state = s;
    return (uint32_t)s;
}

uint64_t fast_rand64(void)
{
    uint64_t s = g_fast_rand_state;
    s ^= s << 13;
    s ^= s >> 7;
    s ^= s << 17;
    g_fast_rand_state = s;
    return s;
}

void fast_rand_seed(uint64_t seed)
{
    g_fast_rand_state = seed ? seed : 0x9e3779b97f4a7c15ULL;
}