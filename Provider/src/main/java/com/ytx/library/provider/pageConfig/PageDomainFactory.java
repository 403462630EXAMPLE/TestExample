package com.ytx.library.provider.pageConfig;


import com.baidao.server.Server;

import java.util.Map;

/**
 * Created by hexi on 16/1/15.
 */
public class PageDomainFactory {
    public static Map<PageDomainType, String> getDomain(Server server, boolean isDebug) {
        return DXDomainConfig.getDomain(isDebug);
    }
}
