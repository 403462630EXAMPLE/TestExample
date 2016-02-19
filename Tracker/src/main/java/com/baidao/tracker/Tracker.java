package com.baidao.tracker;

import android.content.Context;
import android.util.Log;

import com.baidao.tracker.tool.AppUtil;
import com.baidao.tracker.tool.TelephoneUtil;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hexi on 15/3/9.
 */
public class Tracker {
    private static final int THRESHOLD_FOR_PRODUCT = 100;
    private static final int THRESHOLD_FOR_DEBUG = 3;

    private static final String TAG = "Tracker";
    private Context context;

    private int threshold = THRESHOLD_FOR_PRODUCT;
    private List<LogData> data;

    private String screen;
    private String appVersion;
    private String deviceId;
    private String appId;

    private Integer channel;
    private boolean debug;

    private static volatile Tracker instance;

    public static Tracker getInstance(Context context) {
        if (instance == null) {
            instance = new Tracker(context.getApplicationContext());
        }
        return instance;
    }

    public Tracker init(String appId, Integer channel) {
        this.appId = appId;
        this.channel = channel;
        return this;
    }

    public Tracker withDomain(String domain) {
        ApiFactory.setDomain(domain);
        return this;
    }

    public Tracker withDebug(boolean debug) {
        Log.d(TAG, "=====withDebug, debug:" + debug);
        this.debug = debug;
        if (this.debug) {
            this.threshold = THRESHOLD_FOR_DEBUG;
        } else {
            this.threshold = THRESHOLD_FOR_PRODUCT;
        }
        return this;
    }

    private Tracker(Context context) {
        this.context = context;
        this.data = new ArrayList<>();

        this.screen = TelephoneUtil.getScreen(context);
        this.appVersion = AppUtil.getAppVersion(context);
        this.deviceId = TelephoneUtil.getEncodedDeviceId(context);
    }

    public void addLog(LogData logData) {
        Log.d(TAG, "=====addLog, debug:" + debug);
        if (this.debug) {
            return;
        }
        logData.withChannel(channel)
                .withScreen(screen)
                .withAppVersion(appVersion)
                .withDeviceId(deviceId);
        this.data.add(logData);
        if (this.data.size() >= this.threshold) {
            Sender.getInstance(this.context).addToQueue(new Gson().toJson(this.data));
            clear();
        }
    }

    public void save() {
        Log.d(TAG, "=====save, debug:" + debug);
        if (this.debug) {
            return;
        }
        if (this.context != null && this.data.size() > 0) {
            Sender.getInstance(this.context).saveListToDB(new Gson().toJson(data));
            clear();

            Sender.getInstance(this.context).save();
        }
    }

    private void clear()
    {
        this.data = new ArrayList<>();
    }
}
