package com.baidao.domain;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.baidao.data.Agent;
import com.baidao.server.Server;
import com.google.gson.Gson;

import retrofit.RestAdapter;
import rx.Observer;

public class DomainUtil {

    public static boolean isDebug = false;

    public static void setIsDebug(boolean isDebug) {
        DomainUtil.isDebug = isDebug;
    }

    public static void initialize(Context context, PageDomainFactory pageDomainFactory, ServerDomainFactory serverDomainFactory) {
        PageDomain.initialize(context.getApplicationContext(), pageDomainFactory);
        ServerDomain.initialize(context.getApplicationContext(), serverDomainFactory);
    }

    public static void setDOMAIN(Server serverType) {
        Log.d("DOMAIN", "----------" + serverType.name);
        PageDomain.setupDomain(serverType, isDebug);
        ServerDomain.setupDomain(serverType, isDebug);
    }

    public static String getServerDomain(ServerDomainType domainType) {
        return ServerDomain.get(domainType);
    }

    public static String getPageDomain(PageDomainType domainType) {
        return PageDomain.get(domainType);
    }

    public static void setupServerDomain(final Context context, int marketId) {
        getDomainApi().getAgent(marketId, context.getPackageName(), "Android").subscribe(new Observer<Agent>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                Log.e("Domain", "fetch agent error", e);
                Intent intent = new Intent("domain_setup_fail");
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
            }

            @Override
            public void onNext(Agent agent) {
                Log.d("Domain", "agent: " + new Gson().toJson(agent));
                Intent intent = new Intent("domain_setup");
                intent.putExtra("agent", agent);
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
            }
        });
    }

    private static DomainApi getDomainApi() {
        //TODO 是否所有请求agent的domain都是这个？
        return createAdapterBuilder("http://az.compass-service.baidao.com")
                .build()
                .create(DomainApi.class);
    }

    private static RestAdapter.Builder createAdapterBuilder(final String domain) {
        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(domain);
        builder.setLogLevel(RestAdapter.LogLevel.FULL);

        return builder;
    }
}