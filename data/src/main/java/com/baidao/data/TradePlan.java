package com.baidao.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.baidao.data.bagger.IntListBagger;
import com.baidao.data.bagger.StringListBagger;
import com.hannesdorfmann.parcelableplease.annotation.Bagger;
import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;

import java.util.List;

/**
 * Created by rjhy on 16-1-5.
 */
@ParcelablePlease
public class TradePlan implements Parcelable {

    public static final int TYPE_LIVE = 1;
    public static final int TYPE_TRADE_PLAN = 2;

    public static final int STATUS_OVER = 0;
    public static final int STATUS_JOING = 1;
    public static final int STATUS_SERVING = 2;

    public int entryUserCount; //已加入计划的人数
    public int onlineUserCount;  //当前在线人数，视频直播的
    public long currentRecordId; //当前期号
    public boolean living;
    public String livingInfo;
    public int liveStatus;
    public int planStatus; //计划状态，0已结束，代表服务已经到期，只能申购下次的计划了, 1可加入，带表服务还在申购期，倒计时还存在或人数未满, 2赚钱中，代表服务已经开始了，倒计时结束，结束申购
    public long endTime; //本产品下架的时间，即服务结束的时间
    public long startTime; //本产品上架的时间
    public String name;
    public long id;
    public int type;  //1、直播室、2交易计划

    public Ext ext;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        TradePlanParcelablePlease.writeToParcel(this, dest, flags);
    }

    public static final Creator<TradePlan> CREATOR = new Creator<TradePlan>() {
        public TradePlan createFromParcel(Parcel source) {
            TradePlan target = new TradePlan();
            TradePlanParcelablePlease.readFromParcel(target, source);
            return target;
        }

        public TradePlan[] newArray(int size) {
            return new TradePlan[size];
        }
    };

    @ParcelablePlease
    public static class Ext implements Parcelable {
        public String previewImageUrl;
        public String logoImageUrl;
        public String videoIds;
        @Bagger(IntListBagger.class)
        public List<Integer> userTypes;
        public long planSellEndTime;
        public boolean viewInWpb;
        public boolean isVideoRoom;
        public boolean isHotPlan;
        public boolean viewInIndex;
        public String correctRate;
        public String yieldRate;
        public boolean isNewerPlan;
        public boolean isSellOffed;
        public long nextSellTime;
        public String planInfo;
        public boolean isCurrentUserJoined;
        @Bagger(StringListBagger.class)
        public List<String> hotPlanEntryInfos;
        public String realRate;
        public int entryMoney;
        public boolean binding;
        public String domainDir;
        public long planCloseTime;
        public int sellCount;
        public boolean isBigMoney;
        public String bigMoneyTip;
        public long nextSellPlanId;
        public String keyWord;
        public String tags;
        public String operationCopy;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            com.baidao.data.ExtParcelablePlease.writeToParcel(this, dest, flags);
        }

        public static final Creator<Ext> CREATOR = new Creator<Ext>() {
            @Override
            public Ext createFromParcel(Parcel source) {
                Ext target = new Ext();
                com.baidao.data.ExtParcelablePlease.readFromParcel(target, source);
                return target;
            }

            @Override
            public Ext[] newArray(int size) {
                return new Ext[size];
            }
        };
    }
}
