package com.baidao.data;

/**
 * Created by rjhy on 16-1-12.
 */
public class Bid {

    public static final int DIRECTION_BUY = 0;
    public static final int DIRECTION_SELL = 1;

    public static final int WAY_MARKET_PRICE = 0;
    public static final int WAY_LIMIT_PRICE = 1;

    public long id;
    public String note; // 备注
    public int position; // 仓位
    public long createTime; //创建时间
    public String tradeName;; // 交易品种名称
    public int direction; // 操作方向
    public int way; // 操作建议方式
    public double openPrice; // 开仓价或限价
    public double stopPrice; //止损价
    public double targetPrice; // 止盈价
    public long recordId;
    public long refBidId;
    public String nickname; //昵称
    public String headImage;
    public long roomId;
    public String username;
    public int type;
}
