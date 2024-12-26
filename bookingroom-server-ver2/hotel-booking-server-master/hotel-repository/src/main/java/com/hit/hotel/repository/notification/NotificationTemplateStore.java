package com.hit.hotel.repository.notification;

import com.hit.hotel.repository.notification.entity.NotificationTemplate;

public interface NotificationTemplateStore {

    NotificationTemplate getByCode(String code);

}
