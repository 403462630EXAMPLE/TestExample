package com.baidao.notification;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;

import com.baidao.tools.AppUtil;
import com.baidao.tools.BusProvider;
import com.baidao.tools.UserHelper;
import com.baidao.tools.Util;

public abstract class ForegroundHandler extends NotificationHandler {
    public ForegroundHandler(Context context) {
        super(context);
    }

    @Override
    public boolean canHandle(NotificationType dataType) {
        boolean isForeground = AppUtil.isAppOnForeground(context);
        return Util.isScreenOn(context) && isForeground && (dataType != null && dataType.isNeedHandle());
    }

    @Override
    protected PendingIntent getContentIntent(NotificationMessage message) {
        return null;
    }

}
