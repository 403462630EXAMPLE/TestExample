package com.ytx.library.provider.serverConfig;

import com.baidao.server.Server;
import com.ytx.library.provider.ServerDomain;

import java.util.Map;

/**
 * Created by hexi on 16/1/15.
 */
public class ServerDomainFactory {
    public static Map<ServerDomainType, String> getDomain(Server server, boolean isDebug) {
        return DXDomainConfig.getDomain(isDebug);
    }
}
