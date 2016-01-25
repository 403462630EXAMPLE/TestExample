package com.ytx.library.provider;

import com.baidao.server.Server;
import com.ytx.library.provider.serverConfig.ServerDomainFactory;
import com.ytx.library.provider.serverConfig.ServerDomainType;

import java.util.Map;

/**
 * Created by hexi on 16/1/15.
 */
public class ServerDomain {
    private static Map<ServerDomainType, String> DOMAINS;

    public static void setupDomain(Server server, boolean isDebug) {
        DOMAINS = ServerDomainFactory.getDomain(server, isDebug);
    }

    public static String get(ServerDomainType domainType) {
        if (DOMAINS == null) {
            DOMAINS = ServerDomainFactory.getDomain(Server.getDefaultServer(), Domain.isDebug);
        }
        return DOMAINS.get(domainType);
    }
}
