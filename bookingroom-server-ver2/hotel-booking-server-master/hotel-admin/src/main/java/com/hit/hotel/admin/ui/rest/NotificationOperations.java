package com.hit.hotel.admin.ui.rest;

import com.hit.api.factory.GeneralResponse;
import com.hit.common.core.domain.pagination.PaginationRequest;
import com.hit.common.core.domain.response.CommonResponse;
import com.hit.hotel.common.domain.response.NotificationResponse;
import com.hit.hotel.repository.notification.model.NotificationPaginationResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Validated
@RequestMapping("${app.admin-api-path-prefix}/api/v1/notification")
public interface NotificationOperations {

    @Operation(summary = "API get all notification")
    @GetMapping
    ResponseEntity<GeneralResponse<NotificationPaginationResponse<NotificationResponse>>> getNotifications(
            @Valid @ParameterObject PaginationRequest paginationRequest);

    @Operation(summary = "API read notification")
    @PostMapping("/read/{id}")
    ResponseEntity<GeneralResponse<CommonResponse>> readNotification(@PathVariable Long id);

    @Operation(summary = "API read all notification")
    @PostMapping("/read-all")
    ResponseEntity<GeneralResponse<CommonResponse>> readAllNotification();

}
