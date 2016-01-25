package com.baidao.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by rjhy on 15-12-3.
 */
public class DxActivity implements Parcelable {
    public String title;
    public String desc;
    public String img;
    public String shareImg;
    public String galleryImg;
    public String coinPageImg;
    public String coinPageColor;
    public String url;
    public int type;
    public int order;
    public int inReward;
    public int shareReward;
    public int useReward;
    public int share;
    public boolean published;
    public int status;
    public int position;
    public String updateTime;
    public int id;
    public int participate;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.desc);
        dest.writeString(this.img);
        dest.writeString(this.shareImg);
        dest.writeString(this.galleryImg);
        dest.writeString(this.coinPageImg);
        dest.writeString(this.coinPageColor);
        dest.writeString(this.url);
        dest.writeInt(this.type);
        dest.writeInt(this.order);
        dest.writeInt(this.inReward);
        dest.writeInt(this.shareReward);
        dest.writeInt(this.useReward);
        dest.writeInt(this.share);
        dest.writeByte(published ? (byte) 1 : (byte) 0);
        dest.writeInt(this.status);
        dest.writeInt(this.position);
        dest.writeString(this.updateTime);
        dest.writeInt(this.id);
        dest.writeInt(this.participate);
    }

    public DxActivity() {
    }

    private DxActivity(Parcel in) {
        this.title = in.readString();
        this.desc = in.readString();
        this.img = in.readString();
        this.shareImg = in.readString();
        this.galleryImg = in.readString();
        this.coinPageImg = in.readString();
        this.coinPageColor = in.readString();
        this.url = in.readString();
        this.type = in.readInt();
        this.order = in.readInt();
        this.inReward = in.readInt();
        this.shareReward = in.readInt();
        this.useReward = in.readInt();
        this.share = in.readInt();
        this.published = in.readByte() != 0;
        this.status = in.readInt();
        this.position = in.readInt();
        this.updateTime = in.readString();
        this.id = in.readInt();
        this.participate = in.readInt();
    }

    public static final Creator<DxActivity> CREATOR = new Creator<DxActivity>() {
        public DxActivity createFromParcel(Parcel source) {
            return new DxActivity(source);
        }

        public DxActivity[] newArray(int size) {
            return new DxActivity[size];
        }
    };
}
