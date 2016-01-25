package com.ytx.library.provider.pageConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hexi on 16/1/15.
 */
public class DXDomainConfig {
    private static final Map<PageDomainType, String> PRODUCT = new HashMap<PageDomainType, String>() {{
        put(PageDomainType.OPEN_ACCOUNT_PAGE, "http://weixin2.baidao.com/assets/openAccountYG");
        put(PageDomainType.DISCOVER_SCHOOL_PAGE, "http://win.baidao.com/wap/20150428/index.html?tagIds=116&serverId=10");
        put(PageDomainType.DISCOVER_ACTIVITY_PAGE, "http://az.mobile-static-service.baidao.com/activity.list/activity.list.html");
        put(PageDomainType.TRADE_COURSOR_STOP_PROFIT_LOSS_PAGE, "http://img.dx168.com/appweb/app/howto/stop");
        put(PageDomainType.TRADE_COURSOR_DELEGATE_PAGE, "http://img.dx168.com/appweb/app/howto/order");
        put(PageDomainType.CONDITION_ORDER_URL_PAGE, "http://img.dx168.com/appweb/app/howto/condition");
        put(PageDomainType.TRADE_PLAN_PAGE, "http://az.mobile-static-service.baidao.com/gaoshou/index.html");
        put(PageDomainType.LIVE_PAGE, "http://v.dx168.com/liveroom/liveroom-m.html?u=%s");
        put(PageDomainType.APP_USAGE, "http://img.dx168.com/appweb/app/dxterms/agreement/");
    }};

    private static final Map<PageDomainType, String> TEST = new HashMap<PageDomainType, String>() {{
        put(PageDomainType.OPEN_ACCOUNT_PAGE, "http://180.166.190.142/assets/openAccountYG");
        put(PageDomainType.DISCOVER_SCHOOL_PAGE, "http://win.baidao.com/wap/20150428/index.html?tagIds=107&serverId=10&&articleId=44689");
        put(PageDomainType.DISCOVER_ACTIVITY_PAGE, "http://az.mobile-static-service.baidao.com/activity.list/activity.list.html?env=development");
        put(PageDomainType.TRADE_COURSOR_STOP_PROFIT_LOSS_PAGE, "http://img.dx168.com/appweb/app/howto/stop");
        put(PageDomainType.TRADE_COURSOR_DELEGATE_PAGE, "http://img.dx168.com/appweb/app/howto/order");
        put(PageDomainType.CONDITION_ORDER_URL_PAGE, "http://img.dx168.com/appweb/app/howto/condition");
        put(PageDomainType.TRADE_PLAN_PAGE, "http://az.mobile-static-service.baidao.com/gaoshou/index.html");
        put(PageDomainType.LIVE_PAGE, "http://test.v.dx168.com/liveroom/liveroom-m.html?u=%s");
        put(PageDomainType.APP_USAGE, "http://img.dx168.com/appweb/app/dxterms/agreement/");
    }};

    static Map<PageDomainType, String> getDomain(boolean isDebug) {
        if (isDebug) {
            return TEST;
        }
        return PRODUCT;
    }
}
