package com.baidao.domain;

import android.content.Context;

import com.baidao.server.Server;

import java.util.Map;

/**
 * Created by hexi on 16/1/28.
 */
public class PageDomain {
    private static Context context;
    private static PageDomainFactory domainFactory;
    private static Map<PageDomainType, String> DOMAINS;

    public static void initialize(Context context, PageDomainFactory domainFactory) {
        PageDomain.context = context;
        PageDomain.domainFactory = domainFactory;
    }

    public static void setupDomain(Server server, boolean isDebug) {
        DOMAINS = domainFactory.getDomain(context, server, isDebug);
    }

    public static String get(PageDomainType domainType) {
        if (DOMAINS == null) {
            DOMAINS = domainFactory.getDomain(context, Server.getDefaultServer(), DomainUtil.isDebug);
        }
        return DOMAINS.get(domainType);
    }
}
