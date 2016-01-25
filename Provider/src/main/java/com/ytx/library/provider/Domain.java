package com.ytx.library.provider;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.baidao.data.Agent;
import com.baidao.server.Server;
import com.google.gson.Gson;

import rx.Observer;

public class Domain {
    public static boolean isDebug = false;

    public static void setIsDebug(boolean isDebug) {
        Domain.isDebug = isDebug;
    }

    public static void setDOMAIN(Server serverType) {
        Log.d("DOMAIN", "----------" + serverType.name);
        PageDomain.setupDomain(serverType, isDebug);
        ServerDomain.setupDomain(serverType, isDebug);
    }

    public static void setupServerDomain(final Context context, int marketId) {
        RetrofitFactory.getAgentRestAdapter().create(DomainApi.class).getAgent(marketId, context.getPackageName(), "Android").subscribe(new Observer<Agent>() {
            @Override
            public void onCompleted() {}

            @Override
            public void onError(Throwable e) {
                Log.e("Domain", "fetch agent error", e);
                Intent intent = new Intent("dx_domain_setup_fail");
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
            }

            @Override
            public void onNext(Agent agent) {
                Log.d("Domain", "agent: " + new Gson().toJson(agent));
                Intent intent = new Intent("dx_domain_setup");
                intent.putExtra("dx_agent", agent);
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
            }
        });
    }
}