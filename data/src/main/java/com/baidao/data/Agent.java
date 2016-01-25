package com.baidao.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by burizado on 14-12-3.
 */
public class Agent implements Parcelable {
    public String id;
    public String agent;
    public int serverId;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.agent);
        dest.writeInt(this.serverId);
    }

    public Agent() {
    }

    private Agent(Parcel in) {
        this.id = in.readString();
        this.agent = in.readString();
        this.serverId = in.readInt();
    }

    public static final Creator<Agent> CREATOR = new Creator<Agent>() {
        public Agent createFromParcel(Parcel source) {
            return new Agent(source);
        }

        public Agent[] newArray(int size) {
            return new Agent[size];
        }
    };
}
