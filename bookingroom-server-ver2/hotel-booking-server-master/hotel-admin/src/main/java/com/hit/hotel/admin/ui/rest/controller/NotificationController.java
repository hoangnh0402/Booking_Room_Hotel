package com.hit.hotel.admin.ui.rest.controller;

import com.hit.api.factory.GeneralResponse;
import com.hit.api.factory.ResponseFactory;
import com.hit.common.core.domain.pagination.PaginationRequest;
import com.hit.common.core.domain.response.CommonResponse;
import com.hit.hotel.admin.ui.rest.NotificationOperations;
import com.hit.hotel.common.domain.response.NotificationResponse;
import com.hit.hotel.common.service.NotificationService;
import com.hit.hotel.repository.notification.model.NotificationPaginationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController("notificationControllerAdmin")
public class NotificationController implements NotificationOperations {

    private final NotificationService notificationService;

    @Override
    public ResponseEntity<GeneralResponse<NotificationPaginationResponse<NotificationResponse>>>
    getNotifications(PaginationRequest paginationRequest) {
        return ResponseFactory.success(notificationService.getNotifications(paginationRequest));
    }

    @Override
    public ResponseEntity<GeneralResponse<CommonResponse>> readNotification(Long id) {
        return ResponseFactory.success(notificationService.readNotification(id));
    }

    @Override
    public ResponseEntity<GeneralResponse<CommonResponse>> readAllNotification() {
        return ResponseFactory.success(notificationService.readAllNotification());
    }
}
