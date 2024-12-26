package com.hit.hotel.common.domain.mapper;

import com.hit.hotel.common.domain.response.NotificationResponse;
import com.hit.hotel.repository.notification.entity.Notification;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", implementationName = "<CLASS_NAME>CommonImpl")
public abstract class NotificationMapper {

    public abstract NotificationResponse toNotificationResponse(Notification notification);

}
