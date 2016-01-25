package com.baidao.data.e;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rjhy on 15-2-2.
 */
public enum TopMessageType {
    @SerializedName("0") NONE(0, "没有"),
    @SerializedName("1") CHAT(1, "聊天"),
    @SerializedName("2") ACTIVITY(2, "活动"),
    @SerializedName("3") NEWS(3, "资讯"),
    @SerializedName("4") LOTTO(4, "lotto"),
    @SerializedName("5") OPEN_ACCOUNT(5, "开户"),
    @SerializedName("5") EXPERT_ARTICLE(6, "专家文章"),
    @SerializedName("5") EXPERT_NEWS(7, "专家信息"),
    @SerializedName("5") TRADE_PLAN(8, "交易计划");

    private int id;
    private String text;
    TopMessageType(int id, String text) {
        this.id = id;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }
}
