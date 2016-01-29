package com.baidao.androidlibrary.application;

import android.app.Application;

import com.baidao.server.Server;

/**
 * Created by hexi on 16/1/29.
 */
public class BaidaoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Server.setDefaultServer(Server.JD);
    }
}
