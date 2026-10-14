#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/socket.h>
#include <jni.h>
#include <getopt.h>

#include "byedpi/log.h"
#include "byedpi/error.h"
#include "main.h"

extern int server_fd;
static int g_proxy_running = 0;

void clear_params(char *line, char **argv) {
    (void)line;
    (void)argv;
}

JNIEXPORT jint JNICALL
Java_io_github_romanvht_byedpi_core_ByeDpiProxy_jniStartProxy(
        JNIEnv *env, __attribute__((unused)) jobject thiz, jobjectArray args)
{
    if (g_proxy_running) {
        LOG(LOG_S, "proxy already running");
        return -1;
    }

    int argc = (*env)->GetArrayLength(env, args);

    /* +1 слот под NULL-терминатор, который ждут getopt и main */
    char **argv = calloc(argc + 1, sizeof(char *));
    if (!argv) {
        LOG(LOG_S, "failed to allocate memory for argv");
        return -1;
    }

    for (int i = 0; i < argc; i++) {
        jstring arg = (jstring) (*env)->GetObjectArrayElement(env, args, i);
        if (!arg) {
            argv[i] = NULL;
            continue;
        }

        const char *arg_str = (*env)->GetStringUTFChars(env, arg, 0);
        argv[i] = arg_str ? strdup(arg_str) : NULL;
        if (arg_str) (*env)->ReleaseStringUTFChars(env, arg, arg_str);

        (*env)->DeleteLocalRef(env, arg);
    }
    argv[argc] = NULL;

    LOG(LOG_S, "starting proxy with %d args", argc);

    clear_params(NULL, NULL);

    log_init();

    g_proxy_running = 1;
    optind = 1;

    int result = main(argc, argv);

    LOG(LOG_S, "proxy return code %d", result);
    g_proxy_running = 0;

    for (int i = 0; i < argc; i++) free(argv[i]);
    free(argv);

    return result;
}

JNIEXPORT jint JNICALL
Java_io_github_romanvht_byedpi_core_ByeDpiProxy_jniStopProxy(__attribute__((unused)) JNIEnv *env, __attribute__((unused)) jobject thiz)
{
    LOG(LOG_S, "send shutdown to proxy");

    if (!g_proxy_running) {
        LOG(LOG_S, "proxy is not running");
        return -1;
    }

    shutdown(server_fd, SHUT_RDWR);
    g_proxy_running = 0;

    return 0;
}


JNIEXPORT jstring JNICALL
Java_io_github_romanvht_byedpi_core_ByeDpiProxy_jniReadLogs(
        JNIEnv *env, __attribute__((unused)) jclass clazz)
{
    char *s = log_take_str();
    if (!s) {
        return NULL;
    }
    jstring out = (*env)->NewStringUTF(env, s);
    free(s);
    return out;
}

JNIEXPORT jint JNICALL
Java_io_github_romanvht_byedpi_core_ByeDpiProxy_jniForceClose(
        __attribute__((unused)) JNIEnv *env,
        __attribute__((unused)) jclass clazz)
{
    LOG(LOG_S, "closing server socket (fd: %d)", server_fd);
    if (close(server_fd) == -1) {
        LOG(LOG_S, "failed to close server socket (fd: %d)", server_fd);
        return -1;
    }
    LOG(LOG_S, "proxy socket force close");
    g_proxy_running = 0;
    return 0;
}