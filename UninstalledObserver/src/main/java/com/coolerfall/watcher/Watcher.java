package com.coolerfall.watcher;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

/**
 * App uninstall watcher.
 *
 * @author Vincent Cheung
 * @since Jan. 22, 2015
 */
public class Watcher {
    private static final String TAG = Watcher.class.getSimpleName();

    private static final String WATCHER_BIN_NAME = "watcher";

    private static void start(Context context, String url, String parameters, File urlFile, boolean shouldOpenBrowser) {
        String cmd = context.getDir(Command.BIN_DIR_NAME, Context.MODE_PRIVATE)
                .getAbsolutePath() + File.separator + WATCHER_BIN_NAME;

        StringBuilder cmdBuilder = new StringBuilder();
        cmdBuilder.append(cmd);

        try {
            JSONObject parametersObject = new JSONObject(parameters);

            cmdBuilder.append(" -a ");
            cmdBuilder.append(context.getPackageName());
            cmdBuilder.append(" -b ");
            cmdBuilder.append(shouldOpenBrowser ? 1 : 0);
            cmdBuilder.append(" -c ");
            cmdBuilder.append(url);
            cmdBuilder.append(" -d ");
            cmdBuilder.append(parametersObject.get("appVersion"));
            cmdBuilder.append(" -e ");
            cmdBuilder.append(parametersObject.get("channel"));
            cmdBuilder.append(" -f ");
            cmdBuilder.append(parametersObject.get("device"));
            cmdBuilder.append(" -g ");
            cmdBuilder.append(parametersObject.get("deviceId"));
            cmdBuilder.append(" -h ");
            cmdBuilder.append(parametersObject.get("event"));
            cmdBuilder.append(" -i ");
            cmdBuilder.append(parametersObject.get("eventType"));
            cmdBuilder.append(" -j ");
            cmdBuilder.append(parametersObject.get("ip"));
            cmdBuilder.append(" -k ");
            cmdBuilder.append(parametersObject.get("network"));
            cmdBuilder.append(" -l ");
            cmdBuilder.append(parametersObject.get("os"));
            cmdBuilder.append(" -m ");
            cmdBuilder.append(parametersObject.get("osVersion"));
            cmdBuilder.append(" -n ");
            cmdBuilder.append(parametersObject.get("screen"));
            cmdBuilder.append(" -o ");
            cmdBuilder.append(parametersObject.get("company"));
            cmdBuilder.append(" -p ");
            cmdBuilder.append(parametersObject.get("time"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "cmdBuilder: " + cmdBuilder.toString());

        try {
            Runtime.getRuntime().exec(cmdBuilder.toString()).waitFor();
        } catch (IOException | InterruptedException e) {
            Log.e(TAG, "start daemon error: " + e.getMessage());
        }
    }

    /**
     * Run uninstallation watcher.
     *
     * @param context           context
     * @param url               the url to gather uninstallation information
     * @param shouldOpenBrowser should the wathcer open browser or not
     */
    private static void run(final Context context, final String url, final String parameters,
                            final File urlFile, final boolean shouldOpenBrowser) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Command.install(context, WATCHER_BIN_NAME);
                start(context, url, parameters, urlFile, shouldOpenBrowser);
            }
        }).start();
    }

    /**
     * Run uninstall watcher with specified url.
     *
     * @param context           context
     * @param url               the url to gather uninstallation information
     * @param shouldOpenBrowser should open url or not
     */
    public static void run(Context context, String url, String parameters, boolean shouldOpenBrowser) {
        run(context, url, parameters, null, shouldOpenBrowser);
    }

    /**
     * Run uninstall watcher with specified url file if you saved url in files.
     *
     * @param context           context
     * @param urlFile           url file
     * @param shouldOpenBrowser should open url or not
     */
    public static void run(Context context, File urlFile, boolean shouldOpenBrowser) {
        run(context, null, null, urlFile, shouldOpenBrowser);
    }

    /**
     * Run uninstallation watcher.
     *
     * @param context context
     * @param url     the url to gather uninstallation information
     */
    public static void run(Context context, String url) {
        run(context, url, null, false);
    }
}
