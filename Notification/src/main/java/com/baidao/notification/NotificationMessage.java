package com.baidao.notification;

import android.app.Notification;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.baidao.data.GetuiMessage;
import com.google.gson.Gson;
import com.umeng.message.entity.UMessage;

import org.joda.time.LocalDateTime;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class NotificationMessage implements Parcelable {
    public String after_open;
    public boolean play_sound;
    public boolean play_lights;
    public boolean play_vibrate;
    public String sound;
    public String text;
    public String ticker;
    public String title;
    public NotificationType type;
    public Map<String, String> extra = new HashMap<String, String>();

    public int getNotificationId() {
        switch (type) {
            case WARNING_REMIND:
            case TAI_JI_LINE_WARNING:
            case QKX_WARNING:
            case BYJZ_INFOMATION: //预警通知 应该显示多条，不应该覆盖
                return type.getId() + LocalDateTime.now().getMillisOfDay();
            default:
                return type.getId();
        }
    }

    public NotificationMessage() {
    }

    public static NotificationMessage fromUmessage(String message) throws JSONException {
        UMessage uMessage = new UMessage(new JSONObject(message));
        Map<String, String> extra = uMessage.extra;

        NotificationMessage notificationMessage = new NotificationMessage();
        notificationMessage.after_open = uMessage.after_open;
        notificationMessage.title = uMessage.title;
        notificationMessage.text = uMessage.text;
        notificationMessage.type = NotificationType.fromInt(Integer.valueOf(extra.get("dataType")));
        notificationMessage.ticker = uMessage.ticker;
        notificationMessage.play_sound = uMessage.play_sound;
        notificationMessage.play_lights = uMessage.play_lights;
        notificationMessage.play_vibrate = uMessage.play_vibrate;
        notificationMessage.sound = uMessage.sound;
        notificationMessage.extra = extra;
        return notificationMessage;
    }

    public static NotificationMessage fromGetuiMessage(String message) throws JSONException {
        GetuiMessage getuiMessage = new Gson().fromJson(message, GetuiMessage.class);
        Map<String, String> extra = getuiMessage.extra;

        NotificationMessage notificationMessage = new NotificationMessage();
        notificationMessage.after_open = getuiMessage.body.after_open;
        notificationMessage.play_sound = getuiMessage.isPlaySound();
        notificationMessage.play_lights = getuiMessage.isPlayLights();
        notificationMessage.play_vibrate = getuiMessage.isPlayVibrate();
        notificationMessage.sound = getuiMessage.body.sound;
        notificationMessage.text = getuiMessage.body.text;
        notificationMessage.ticker = getuiMessage.body.ticker;
        notificationMessage.title = getuiMessage.body.title;
        notificationMessage.type = NotificationType.fromInt(Integer.parseInt(extra.get("dataType")));
        notificationMessage.extra = extra;
        return notificationMessage;
    }

    public Uri getSound(String packageName) {
        return Uri.parse(String.format("android.resource://%s/raw/"+this.sound, packageName));
    }

    public int getDefaults() {
        int defaults = 0;
        if (play_sound && TextUtils.isEmpty(sound)) {
            defaults |= Notification.DEFAULT_SOUND;
        }
        if (play_lights) {
            defaults |= Notification.DEFAULT_LIGHTS;
        }
        if (play_vibrate) {
            defaults |= Notification.DEFAULT_VIBRATE;
        }

        return defaults;
    }


    public boolean getAutoCancel() {
        switch (type.getValue()) {

            default:
                return true;
        }
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.after_open);
        dest.writeByte(play_sound ? (byte) 1 : (byte) 0);
        dest.writeByte(play_lights ? (byte) 1 : (byte) 0);
        dest.writeByte(play_vibrate ? (byte) 1 : (byte) 0);
        dest.writeString(this.sound);
        dest.writeString(this.text);
        dest.writeString(this.ticker);
        dest.writeString(this.title);
        dest.writeInt(this.type == null ? -1 : this.type.ordinal());
        dest.writeMap(this.extra);
    }

    protected NotificationMessage(Parcel in) {
        this.after_open = in.readString();
        this.play_sound = in.readByte() != 0;
        this.play_lights = in.readByte() != 0;
        this.play_vibrate = in.readByte() != 0;
        this.sound = in.readString();
        this.text = in.readString();
        this.ticker = in.readString();
        this.title = in.readString();
        int tmpType = in.readInt();
        this.type = tmpType == -1 ? null : NotificationType.values()[tmpType];
        this.extra = in.readHashMap(Map.class.getClassLoader());
    }

    public static final Creator<NotificationMessage> CREATOR = new Creator<NotificationMessage>() {
        public NotificationMessage createFromParcel(Parcel source) {
            return new NotificationMessage(source);
        }

        public NotificationMessage[] newArray(int size) {
            return new NotificationMessage[size];
        }
    };
}
