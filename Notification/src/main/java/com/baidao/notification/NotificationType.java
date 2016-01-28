package com.baidao.notification;

public enum  NotificationType {

    /**
     * 修改次数的 type 同时记得修改 RedDotHelper 中对应的 Type
     */

    NOTE_REMIND(0, 1001, true, "纸条提醒"),
    OPERATION_SUGGESTION_REMIND(1, 1002, true, "操作建议提醒"),
    NEWS_REMIND(2, 1003, true, "快讯提醒"),
    WARNING_REMIND(3, 1004, true, "预警提醒"),
    TODAY_STRATEGY_REMIND(4, 1005, false, "今日策略提醒"),
    CALENDAR_REMIND(5, 1006, false, "日历提醒"),
    REMIND(6, 1007, false, "提醒"),
    EMP_BIND_SUCCESS(7, 1008, true, "投顾绑定成功"),
    EMP_SEND_NEWS(8, 1009, true, "投顾发来新消息"),
    LOTTO(9, 1010, false, "乐透"),
    TAI_JI_LINE_WARNING(10, 1011, true, "太极线预警"),
    NEWS_MOVE(11, 1012, true, "行情异动"),
    ACTIVITY_INFORMATION(12, 1013, true, "活动资讯"),
    EXPERT_ARTICLE(13, 1014, false, "专家文章"),
    OPEN_ACCOUNT_INFORMATION(14, 1015, false, "开户资讯"),
    NEWS(15, 1016, false, "消息"),
    INTERACTION(16, 1017, true, "互动"),
    FEEDBACK(17, 1018, true, "建议反馈"),
    QKX_WARNING(18, 1019, true, "乾坤线预警"),
    LIVE_OPEN(19, 1020, true, "直播室推送"),
    VISITOR(20, 1021, true, "访客消息"),
    BYJZ_INFOMATION(21, 1022, true, "博弈矩阵交易信号推送"),

    BUY_ORDER(5001, 5001, true, "南方稀贵操作通知买单"),
    SELL_ORDER(5002, 5002, true, "南方稀贵操作通知卖单"),
    STOP_PROFIT_LOSS_REMIND(5003, 5003, true, "南方稀贵止盈止损"),
    STRATEGY(5004, 5004, true, "南方稀贵老师说策略"),
    ACTIVITY_STOPPED(5005, 5005, true, "南方稀贵服务结束"),
    OPEN_REMIND(5006, 5006, true, "南方稀贵开抢提醒"),
    DELEGATE_ORDER(5007, 5007, true, "南方稀贵委托单成交"),
    CONDITION_ORDER(5008, 5008, true, "南方稀贵条件单触发"),
    STOP_PROFIT_LOSS_ORDER(5009, 5009, true, "南方稀贵止盈止触发"),

    CHAT_MESSAGE(6001, 6001, true, "聊天消息推送");

    private int value;
    private int id;
    private boolean needHandle;
    private String desc;

    public String getDesc() {
        return desc;
    }

    NotificationType(int value, int id, boolean needHandle, String desc) {
        this.value = value;
        this.id = id;
        this.needHandle = needHandle;
        this.desc = desc;
    }
    public int getValue() {
        return value;
    }
    public int getId() {
        return id;
    }

    public boolean isNeedHandle() {
        return needHandle;
    }

    public static NotificationType fromInt(int value) {
        for (NotificationType notificationType : values()) {
            if (notificationType.value == value) {
                return notificationType;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return this.desc;
    }
}
