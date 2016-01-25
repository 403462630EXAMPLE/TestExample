package com.baidao.data;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

/**
 * Created by burizado on 14-12-12.
 */
public class User {

    private boolean canBind = false;
    private boolean canChange = false;
    private String cusUniqueId = "";
    private boolean hasBindCurrRoom = false;
    private boolean hasPhone = false;
    private String imgUrl = "";
    private boolean isBozhu = false;
    private boolean isExpert = false;
    private String loginPlatform = "";
    private int needCnt = -1;
    private boolean nextCanChange = false;
    private String nickname = "";
    private boolean online = false;
    private int scoreCnt = -1;
    private int serverId = 10;
    private int uid = -1;
    private int userStatus = -1;
    private int userType = 0;
    private String username = "";
    private String tradeAccount;

    private long cusId;
    @SerializedName("DX_NFXG_ownerCsrId")
    private long csrId;
    @SerializedName("DX_NFXG_ownerCsrNickName")
    private String csrName;
    @SerializedName("DX_NFXG_ownerCsrAvatar")
    private String csrAvatar;

    public Csr getCsr() {
        return new Csr(csrId, csrAvatar, csrName);
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public boolean canBind() {
        return canBind;
    }

    public boolean canChange() {
        return canChange;
    }

    public String getCusUniqueId() {
        return cusUniqueId;
    }

    public void setCusUniqueId(String cusUniqueId) {
        this.cusUniqueId = cusUniqueId;
    }

    public boolean hasBindCurrRoom() {
        return hasBindCurrRoom;
    }

    public boolean hasPhone() {
        return hasPhone;
    }

    public void setHasPhone(boolean hasPhone) {
        this.hasPhone = hasPhone;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public boolean isBozhu() {
        return isBozhu;
    }

    public boolean isExpert() {
        return isExpert;
    }

    public void setExpert(boolean isExpert) {
        this.isExpert = isExpert;
    }

    public String getLoginPlatform() {
        return loginPlatform;
    }

    public void setLoginPlatform(String loginPlatform) {
        this.loginPlatform = loginPlatform;
    }

    public int getNeedCnt() {
        return needCnt;
    }

    public void setNeedCnt(int needCnt) {
        this.needCnt = needCnt;
    }

    public boolean nextCanChange() {
        return nextCanChange;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public boolean online() {
        return online;
    }

    public int getScoreCnt() {
        return scoreCnt;
    }

    public void setScoreCnt(int scoreCnt) {
        this.scoreCnt = scoreCnt;
    }

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(int userStatus) {
        this.userStatus = userStatus;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setTradeAccount(String tradeAccount) {
        this.tradeAccount = tradeAccount;
    }

    public String getTradeAccount() {
        return tradeAccount;
    }

    public String getHXID() {
        return "dx" + username;
    }

    public long getCusId() {
        return cusId;
    }
}
