package com.hit.hotel.repository.notification.constant;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum NotificationTemplateEnum {

    BOOKING("BOOKING"),
    CANCEL_BOOKING("CANCEL_BOOKING");

    private final String code;

    public String code() {
        return code;
    }
}
