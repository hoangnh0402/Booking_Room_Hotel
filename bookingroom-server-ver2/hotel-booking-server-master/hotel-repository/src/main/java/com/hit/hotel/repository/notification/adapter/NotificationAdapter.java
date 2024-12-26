package com.hit.hotel.repository.notification.adapter;

import com.hit.common.core.domain.pagination.PaginationRequest;
import com.hit.common.utils.PaginationUtils;
import com.hit.hotel.repository.notification.NotificationStore;
import com.hit.hotel.repository.notification.entity.Notification;
import com.hit.hotel.repository.notification.model.NotificationPaginationResponse;
import com.hit.hotel.repository.notification.repository.NotificationRepository;
import com.hit.jpa.BaseJPAAdapter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class NotificationAdapter extends BaseJPAAdapter<Notification, Long, NotificationRepository> implements NotificationStore {

    @Override
    protected Class<Notification> getEntityClass() {
        return Notification.class;
    }

    @Override
    public void readAllNotification() {
        this.repository.readAllNotification();
    }

    @Override
    public NotificationPaginationResponse<Notification> getNotifications(PaginationRequest request) {
        Pageable pageable = PaginationUtils.buildPageableJPQL(request);
        Page<Notification> notifications = this.repository.getNotifications(pageable);
        Integer totalUnreadNotifications = this.repository.countByIsRead(Boolean.FALSE);
        return new NotificationPaginationResponse<>(request, notifications, totalUnreadNotifications);
    }
}
