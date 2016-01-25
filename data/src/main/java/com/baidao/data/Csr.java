package com.baidao.data;

import com.google.gson.Gson;

/**
 * Created by chengxin on 1/4/16.
 */
public class Csr {
    private long id;
    private String avatar;

    private String nickname;

    public Csr() {}

    public Csr(long id, String avatar, String nickname) {
        this.id = id;
        this.avatar = avatar;
        this.nickname = nickname;
    }

    public long getCsrId() {
        return id;
    }

    public String getHXID() {
        return "CRM" + id;
    }

    public String getAvatar() {
        return avatar;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
