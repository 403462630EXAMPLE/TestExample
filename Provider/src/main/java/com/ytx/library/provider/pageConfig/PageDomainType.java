package com.ytx.library.provider.pageConfig;

/**
 * Created by hexi on 16/1/15.
 */
public enum PageDomainType {
    OPEN_ACCOUNT_PAGE("openAccountPage"),
    DISCOVER_SCHOOL_PAGE("discover_school"),
    DISCOVER_ACTIVITY_PAGE("discover_activity"),
    TRADE_COURSOR_STOP_PROFIT_LOSS_PAGE("trade_coursor_stop_profit_loss"),
    TRADE_COURSOR_DELEGATE_PAGE("trade_coursor_delegate"),
    CONDITION_ORDER_URL_PAGE("condition_order_url"),
    TRADE_PLAN_PAGE("trade_plan_page"),
    LIVE_PAGE("live"),
    APP_USAGE("app_usage");

    public String type;

    PageDomainType(String type) {
        this.type = type;
    }
}
