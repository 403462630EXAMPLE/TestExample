package com.ytx.library.provider;

import com.baidao.server.Server;
import com.ytx.library.provider.pageConfig.PageDomainFactory;
import com.ytx.library.provider.pageConfig.PageDomainType;

import java.util.Map;

/**
 * Created by hexi on 16/1/15.
 */
public class PageDomain {
    private static Map<PageDomainType, String> DOMAINS;

    public static void setupDomain(Server server, boolean isDebug) {
        DOMAINS = PageDomainFactory.getDomain(server, isDebug);
    }

    public static String get(PageDomainType domainType) {
        if (DOMAINS == null) {
            DOMAINS = PageDomainFactory.getDomain(Server.getDefaultServer(), Domain.isDebug);
        }
        return DOMAINS.get(domainType);
    }
}
