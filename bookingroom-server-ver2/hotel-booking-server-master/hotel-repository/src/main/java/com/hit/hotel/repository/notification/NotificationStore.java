package com.hit.hotel.repository.notification;

import com.hit.common.core.domain.pagination.PaginationRequest;
import com.hit.hotel.repository.notification.entity.Notification;
import com.hit.hotel.repository.notification.model.NotificationPaginationResponse;

public interface NotificationStore {

    Notification get(Long id);

    Notification save(Notification notification);

    void readAllNotification();

    NotificationPaginationResponse<Notification> getNotifications(PaginationRequest request);

}
