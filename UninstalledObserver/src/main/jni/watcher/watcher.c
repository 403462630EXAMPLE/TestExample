/*
 * File        : watcher.c
 * Author      : Vincent Cheung
 * Date        : Jan. 21, 2015
 * Description : This is used to watch if the app is uninstall.
 *
 * Copyright (C) Vincent Chueng<coolingfall@gmail.com>
 *
 */

#include <errno.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <signal.h>
#include <unistd.h>
#include <time.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <sys/time.h>
#include <sys/inotify.h>
#include <sys/wait.h>

#include "chttp.h"
#include "common.h"

#define BUFFER_SIZE     4 * 1024
#define LOG_TAG         "Watcher"

/* child process signal function */
static void sig_child()
{
	int status;
	while(waitpid(-1, &status, WNOHANG) > 0);

	return;
}

int main(int argc, char *argv[]) {
	int i;
	int should_open_browser = 0;
	char *package_name = NULL;
	char *url = NULL;

    char *app_version = NULL;
    char *channel = NULL;
    char *device = NULL;
    char *device_id = NULL;
    char *event = NULL;
    char *event_type = NULL;
    char *ip = NULL;
    char *network = NULL;
    char *os = NULL;
    char *os_version = NULL;
    char *screen = NULL;
    char *company = NULL;
    char *time = NULL;

	for (i = 0; i < argc; i ++) {
		if (!strcmp("-a", argv[i])) {
			package_name = argv[i + 1];
			LOGD(LOG_TAG, "package name: %s", package_name);
		}

		if (!strcmp("-b", argv[i])) {
			should_open_browser = atoi(argv[i + 1]);
			LOGD(LOG_TAG, "should open brwoser: %d", should_open_browser);
		}

		if (!strcmp("-c", argv[i])) {
			url = argv[i + 1];
			LOGD(LOG_TAG, "url: %s", url);
		}

		if (!strcmp("-d", argv[i])) {
        	app_version = argv[i + 1];
        	LOGD(LOG_TAG, "app_version: %s", app_version);
        }

		if (!strcmp("-e", argv[i])) {
        	channel = argv[i + 1];
        	LOGD(LOG_TAG, "channel: %s", channel);
        }

		if (!strcmp("-f", argv[i])) {
        	device = argv[i + 1];
        	LOGD(LOG_TAG, "device: %s", device);
        }

		if (!strcmp("-g", argv[i])) {
        	device_id = argv[i + 1];
        	LOGD(LOG_TAG, "device_id: %s", device_id);
        }

        if (!strcmp("-h", argv[i])) {
        	event = argv[i + 1];
        	LOGD(LOG_TAG, "event: %s", event);
        }

        if (!strcmp("-i", argv[i])) {
        	event_type = argv[i + 1];
        	LOGD(LOG_TAG, "eventType: %s", event_type);
        }

        if (!strcmp("-j", argv[i])) {
        	ip = argv[i + 1];
        	LOGD(LOG_TAG, "ip: %s", ip);
		}

		if (!strcmp("-k", argv[i])) {
			network = argv[i + 1];
			LOGD(LOG_TAG, "network: %s", network);
		}

		if (!strcmp("-l", argv[i])) {
			os = argv[i + 1];
			LOGD(LOG_TAG, "os: %s", os);
		}

		if (!strcmp("-m", argv[i])) {
			os_version = argv[i + 1];
			LOGD(LOG_TAG, "os_version: %s", os_version);
		}

		if (!strcmp("-n", argv[i])) {
			screen = argv[i + 1];
			LOGD(LOG_TAG, "screen: %s", screen);
		}

		if (!strcmp("-o", argv[i])) {
			company = argv[i + 1];
			LOGD(LOG_TAG, "company: %s", company);
		}

		if (!strcmp("-p", argv[i])) {
			time = argv[i + 1];
			LOGD(LOG_TAG, "time: %s", time);
		}
	}

	/* get the directory for watcher */
	char *app_dir = str_stitching("/data/data/", package_name);
	char *lib_dir = str_stitching(app_dir, "/lib");
	char *watch_file_path = str_stitching(app_dir, "/uninstall.watch");

	/* the file path should not be null */
	if (watch_file_path == NULL)
	{
		LOGE(LOG_TAG, "watch file path is NULL");
		exit(EXIT_FAILURE);
	}

	/* avoid zombie process */
	signal(SIGCHLD, sig_child);

	/* find pid by name and kill them */
	int pid_list[100];
	int total_num = find_pid_by_name(argv[0], pid_list);
	for (i = 0; i < total_num; i ++)
	{
		int retval = 0;
		int watcher_pid = pid_list[i];
		if (watcher_pid > 1 && watcher_pid != getpid())
		{
			retval = kill(watcher_pid, SIGKILL);
			if (!retval)
            {
                LOGD(LOG_TAG, "kill watcher process success: %d", watcher_pid);
            }
            else
            {
                LOGD(LOG_TAG, "kill wathcer process %d fail: %s", watcher_pid, strerror(errno));
                exit(EXIT_SUCCESS);
            }
		}
	}

	/* get child process */
	pid_t pid = fork();
	if (pid < 0)
	{
		LOGE(LOG_TAG, "fork failed");
	}
	else if (pid == 0)
	{
		/* inotify init */
		int fd = inotify_init();
		if (fd < 0)
		{
			LOGE(LOG_TAG, "inotify_init init failed");
			exit(EXIT_FAILURE);
		}

		int w_fd = open(watch_file_path, O_RDWR | O_CREAT | O_TRUNC,
				S_IRWXU | S_IRWXG | S_IRWXO);
		if (w_fd < 0)
		{
			LOGE(LOG_TAG, "open watch file error");
			exit(EXIT_FAILURE);
		}

		close(w_fd);

		/* add watch in inotify */
		int watch_fd = inotify_add_watch(fd, watch_file_path, IN_DELETE);
		if (watch_fd < 0)
		{
			LOGE(LOG_TAG, "inotify_add_watch failed");
			exit(EXIT_FAILURE);
		}

		void *p_buf = malloc(sizeof(struct inotify_event));
		if (p_buf == NULL)
		{
			LOGD(LOG_TAG, "malloc inotify event failed");
			exit(EXIT_FAILURE);
		}

		LOGD(LOG_TAG, "watcher process fork ok, start to watch");

		while (1)
		{
			/* read will block process */
			size_t read_bytes = read(fd, p_buf, sizeof(struct inotify_event));

			/* delay 200ms */
			usleep(200*1000);

			/* to check if the app has uninstalled, indeed */
			FILE *lib_dir_file = fopen(lib_dir, "r");
			FILE *app_dir_file = fopen(app_dir, "r");
			if (lib_dir_file == NULL || app_dir_file == NULL)
			{
				break;
			}
			else
			{
				/* close app dir file */
				fclose(lib_dir_file);
				fclose(app_dir_file);

				/* add notify watch again */
				int w_fd = open(watch_file_path, O_WRONLY | O_CREAT | O_TRUNC,
						S_IRWXU | S_IRWXG | S_IRWXO);
				close(w_fd);

				int watch_fd = inotify_add_watch(fd, watch_file_path, IN_DELETE);
				if (watch_fd < 0)
				{
					LOGE(LOG_TAG, "inotify_add_watch failed");
					free(p_buf);
					exit(EXIT_FAILURE);
				}
			}
		}

		free(p_buf);
		inotify_rm_watch(fd, IN_DELETE);
		LOGD(LOG_TAG, "the app has been uninstalled, call url");

		/* call url */
		chttp_post(url, app_version, channel, device, device_id, event, event_type, ip, network, os, os_version, screen, company, time);

		/* open browser if needed */
		if (should_open_browser)
		{
			LOGD(LOG_TAG, "open browser");
			open_browser(url);
		}

		exit(EXIT_SUCCESS);
	}
	else
	{
		/* parent process */
		exit(EXIT_SUCCESS);
	}
}