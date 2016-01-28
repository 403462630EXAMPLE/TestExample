package com.baidao.domain;

import android.content.Context;

import com.baidao.server.Server;

import java.util.Map;

/**
 * Created by hexi on 16/1/28.
 */
public class ServerDomain {
    private static Context context;
    private static ServerDomainFactory domainFactory;
    private static Map<ServerDomainType, String> DOMAINS;

    public static void initialize(Context context, ServerDomainFactory domainFactory) {
        ServerDomain.context = context;
        ServerDomain.domainFactory = domainFactory;
    }

    public static void setupDomain(Server server, boolean isDebug) {
        DOMAINS = domainFactory.getDomain(context, server, isDebug);
    }

    public static String get(ServerDomainType domainType) {
        if (DOMAINS == null) {
            DOMAINS = domainFactory.getDomain(context, Server.getDefaultServer(), DomainUtil.isDebug);
        }
        return DOMAINS.get(domainType);
    }
}
