package com.baidao.notification;

import java.util.ArrayList;
import java.util.List;

public class NotificationProcessor {
    public interface NotificationProcessListener {
        public void afterHandleMessage(NotificationMessage message);
    }

    List<NotificationHandler> handlers = new ArrayList<>();
    private NotificationProcessListener notificationProcessListener;
    static private NotificationProcessor processor;

    private NotificationProcessor(){}

    static public NotificationProcessor getInstance() {
        if (processor == null) {
            processor = new NotificationProcessor();
        }
        return processor;
    }

    public void clear() {
        processor.handlers.clear();
    }

    public void registerHandler(NotificationHandler handler) {
        if (handlers.contains(handler)) return;
        handlers.add(handler);
    }

    public void setNotificationProcessListener(NotificationProcessListener listener) {
        this.notificationProcessListener = listener;
    }

    public void processNotification(NotificationMessage message) {
        for (NotificationHandler handler : handlers) {
            if (handler.canHandle(message.type)) {
                handler.handle(message);

                if (notificationProcessListener != null) {
                    notificationProcessListener.afterHandleMessage(message);
                }
            }
        }
    }
}
