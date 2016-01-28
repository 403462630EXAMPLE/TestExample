package com.baidao.notification;

import android.content.Context;

import com.baidao.tools.AppUtil;
import com.baidao.tools.Util;

public abstract class BackgroundHandler extends NotificationHandler {
    public BackgroundHandler(Context context) {
        super(context);
    }

    @Override
    public boolean canHandle(NotificationType dataType) {
        boolean isForeground = AppUtil.isAppOnForeground(context);
        return (!Util.isScreenOn(context) || !isForeground) && (dataType != null && dataType.isNeedHandle());
    }

}
