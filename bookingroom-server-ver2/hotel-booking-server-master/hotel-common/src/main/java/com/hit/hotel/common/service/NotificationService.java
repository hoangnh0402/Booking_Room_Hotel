package com.hit.hotel.common.service;

import com.hit.common.core.domain.pagination.PaginationRequest;
import com.hit.common.core.domain.response.CommonResponse;
import com.hit.hotel.common.domain.response.NotificationResponse;
import com.hit.hotel.repository.notification.constant.NotificationTemplateEnum;
import com.hit.hotel.repository.notification.model.NotificationPaginationResponse;

import java.time.LocalDateTime;
import java.util.Collection;

public interface NotificationService {

    NotificationPaginationResponse<NotificationResponse> getNotifications(PaginationRequest request);

    CommonResponse readNotification(Long notificationId);

    CommonResponse readAllNotification();

    void pushNotification(NotificationTemplateEnum template, Object objectContent);

    void pushEventRoomStatus(LocalDateTime checkIn, LocalDateTime checkOut, String status, Collection<Integer> roomIds);

}
