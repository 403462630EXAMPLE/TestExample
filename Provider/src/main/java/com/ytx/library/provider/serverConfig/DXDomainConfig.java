package com.ytx.library.provider.serverConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hexi on 16/1/15.
 */
public class DXDomainConfig {
    private static Map<ServerDomainType, String> PRODUCT = new HashMap<ServerDomainType, String>() {{
        put(ServerDomainType.AGENT, "http://az.compass-service.baidao.com");
        put(ServerDomainType.QUOTES, "http://api.baidao.com");
        put(ServerDomainType.JRY, "http://dx.device.baidao.com");
        put(ServerDomainType.USER_CENTER, "http://dx.device.baidao.com");
        put(ServerDomainType.EASECHAT, "http://192.168.19.164:9190/");
        put(ServerDomainType.MOBILESERVICE, "http://dx.mobile-service.baidao.com");
        put(ServerDomainType.STATISTICS, "http://app-logs.baidao.com/post");
        put(ServerDomainType.LOGINSERVER, "http://m.gwstation.baidao.com:5063");
        put(ServerDomainType.USER_PERMISSION, "http://api.baidao.com/ucenter-permission");
        put(ServerDomainType.TRADE_PLAN, "http://v.dx168.com/live-apis");
        put(ServerDomainType.OPEN_ACCOUNT_SERVER, "http://kh-yg.baidao.com");

    }};

    private static Map<ServerDomainType, String> TEST = new HashMap<ServerDomainType, String>() {{
        put(ServerDomainType.AGENT, "http://az.compass-service.baidao.com");
        put(ServerDomainType.QUOTES, "http://117.74.136.35:6092");
        put(ServerDomainType.JRY, "http://test.dx.device.baidao.com");
        put(ServerDomainType.USER_CENTER, "http://test.dx.device.baidao.com");
        put(ServerDomainType.EASECHAT, "http://192.168.19.164:9190/");
        put(ServerDomainType.MOBILESERVICE, "http://test.dx.mobile-service.baidao.com");
        put(ServerDomainType.STATISTICS, "http://192.168.26.30:12121");
        put(ServerDomainType.LOGINSERVER, "http://117.74.136.35:5063");
        put(ServerDomainType.USER_PERMISSION, "http://192.168.26.30:8084");
        put(ServerDomainType.TRADE_PLAN, "http://test.v.dx168.com/live-apis");
        put(ServerDomainType.OPEN_ACCOUNT_SERVER, "http://kh-yg.baidao.com");

    }};

    static Map<ServerDomainType, String> getDomain(boolean isDebug) {
        if (isDebug) {
            return TEST;
        }
        return PRODUCT;
    }
}
