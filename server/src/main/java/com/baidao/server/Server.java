package com.baidao.server;

import android.content.Context;
import android.content.SharedPreferences;

import com.baidao.persistence.SharedPreferenceUtil;

public enum Server {
    TT(1, "tt", "02195049"),
    TD(2, "td", "4008213988"),
    YG(3, "yg", "4001616003"),
    SSY(8, "ssy", "02136129099"),//深石油
    BSY(9, "bsy", "4001858003"),//北石油
    DX(10, "dx", "400-1611-003"),
    JD(11, "jd", "02195049");

    public int serverId;
    public String name;
    public String phoneNumber;

    private static Server defaultServer;
    public static final String DEFAULT_SERVER_KEY = "server.default.server";

    public static void setDefaultServer(Context context, Server server) {
        defaultServer = server;
        saveToCache(context, server);
    }

    private static void saveToCache(Context context, Server server) {
        SharedPreferences sharedPreferences = SharedPreferenceUtil.getSharedPreference(context);
        sharedPreferences.edit()
                .putInt(DEFAULT_SERVER_KEY, server.serverId)
                .commit();
    }

    public static Server getDefaultServer(Context context) {
        if (defaultServer == null) {
            defaultServer = getServerFromCache(context);
        }
        if (defaultServer == null) {
            throw new IllegalStateException("@@@please set default server first@@@");
        }
        return defaultServer;
    }

    private static Server getServerFromCache(Context context) {
        int serverId = SharedPreferenceUtil.getSharedPreference(context).getInt(DEFAULT_SERVER_KEY, -1);
        for (Server server : values()) {
            if (server.serverId == serverId) {
                return server;
            }
        }
        return null;
    }

    private Server(int serverId, String name, String phoneNumber) {
        this.serverId = serverId;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public static Server from(Context context, int serverId) {
        for (Server server : values()) {
            if (server.serverId == serverId) {
                return server;
            }
        }
        return getServerFromCache(context);
    }

    public String getName() {
        return name;
    }
}
