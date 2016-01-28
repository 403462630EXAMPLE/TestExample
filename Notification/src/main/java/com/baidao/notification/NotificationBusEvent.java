package com.baidao.notification;

public class NotificationBusEvent {
    public NotificationType type;

    public NotificationBusEvent(NotificationType type) {
        this.type = type;
    }
}
