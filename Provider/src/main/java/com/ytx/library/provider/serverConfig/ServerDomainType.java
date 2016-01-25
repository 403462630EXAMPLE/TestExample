package com.ytx.library.provider.serverConfig;

/**
 * Created by hexi on 16/1/15.
 */
public enum ServerDomainType {
    AGENT("agent"),
    QUOTES("quotes"),
    QUOTESROOTER("quotesRooter"),
    WWW("www"),
    JRY("jry"),
    USER_CENTER("userCenter"),
    EASECHAT("chat"),
    MOBILESERVICE("mobileService"),
    AUDIO("audio"),
    TJ_PERMISSION("tjPermission"),
    LOGINSERVER("loginServer"),
    STATISTICS("statistics"),
    USER_PERMISSION("userPermission"),
    CRM("crm"),
    QUERY_USER("query_user"),
    OPEN_ACCOUNT_SERVER("open_account_server"),
    TRADE_PLAN("trade_plan");

    public String type;

    ServerDomainType(String type) {
        this.type = type;
    }
}
