package com.hit.hotel.common.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.hit.common.core.constants.CommonConstants;
import com.hit.common.core.domain.pagination.PaginationRequest;
import com.hit.common.core.domain.response.CommonResponse;
import com.hit.common.core.exception.BaseResponseException;
import com.hit.common.core.exception.ResponseStatusCodeEnum;
import com.hit.common.core.json.JsonMapper;
import com.hit.common.utils.TimeUtils;
import com.hit.hotel.common.domain.mapper.NotificationMapper;
import com.hit.hotel.common.domain.response.NotificationResponse;
import com.hit.hotel.common.service.NotificationService;
import com.hit.hotel.repository.notification.NotificationStore;
import com.hit.hotel.repository.notification.NotificationTemplateStore;
import com.hit.hotel.repository.notification.constant.NotificationTemplateEnum;
import com.hit.hotel.repository.notification.entity.Notification;
import com.hit.hotel.repository.notification.entity.NotificationTemplate;
import com.hit.hotel.repository.notification.model.NotificationPaginationResponse;
import com.hit.hotel.socket.client.constant.SocketConstants;
import com.hit.hotel.socket.client.service.SocketClientService;
import liqp.TemplateParser;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationStore notificationStore;

    private final NotificationTemplateStore notificationTemplateStore;

    private final SocketClientService socketClientService;

    private final NotificationMapper notificationMapper;

    @Qualifier("threadPoolTaskExecutorNotification")
    private final ThreadPoolTaskExecutor threadPoolTaskExecutor;

    private static final TemplateParser TEMPLATE_PARSER = TemplateParser.DEFAULT;

    @Override
    public NotificationPaginationResponse<NotificationResponse> getNotifications(PaginationRequest request) {
        if (StringUtils.isEmpty(request.getSortBy())) {
            request.setSortBy("createdDate");
            request.setSortType(PaginationRequest.Direction.DESC.name());
        }
        return notificationStore.getNotifications(request).map(notificationMapper::toNotificationResponse);
    }

    @Override
    public CommonResponse readNotification(Long notificationId) {
        Notification notification = notificationStore.get(notificationId);
        if (ObjectUtils.isEmpty(notification)) {
            throw new BaseResponseException(ResponseStatusCodeEnum.RESOURCE_NOT_FOUND);
        }
        notification.setIsRead(Boolean.TRUE);
        notificationStore.save(notification);
        return new CommonResponse(Boolean.TRUE, CommonConstants.UPDATE_SUCCESS);
    }

    @Override
    public CommonResponse readAllNotification() {
        notificationStore.readAllNotification();
        return new CommonResponse(Boolean.TRUE, CommonConstants.UPDATE_SUCCESS);
    }

    @Override
    public void pushNotification(NotificationTemplateEnum template, Object objectContent) {
        threadPoolTaskExecutor.execute(() -> {
            NotificationTemplate notifyTemplate = notificationTemplateStore.getByCode(template.code());
            if (ObjectUtils.isEmpty(notifyTemplate)) {
                throw new BaseResponseException(ResponseStatusCodeEnum.RESOURCE_NOT_FOUND);
            }
            TypeReference<Map<String, Object>> mapTypeRef = new TypeReference<>() {
            };
            Map<String, Object> dataContent = JsonMapper.getObjectMapper().convertValue(objectContent, mapTypeRef);

            String path = StringUtils.isEmpty(notifyTemplate.getPathTemplate()) ? null :
                    TEMPLATE_PARSER.parse(notifyTemplate.getPathTemplate()).render(dataContent).trim();
            String content = TEMPLATE_PARSER.parse(notifyTemplate.getContentTemplate()).render(dataContent).trim();
            Notification notification = new Notification()
                    .setTitle(notifyTemplate.getTitle())
                    .setContent(content)
                    .setPath(path)
                    .setIsRead(Boolean.FALSE);
            Notification notificationSaved = notificationStore.save(notification);
            socketClientService.sendCommand(SocketConstants.Room.ADMIN, SocketConstants.Event.NOTIFICATION,
                    notificationMapper.toNotificationResponse(notificationSaved));
        });
    }

    @Override
    public void pushEventRoomStatus(LocalDateTime checkIn, LocalDateTime checkOut, String status,
                                    Collection<Integer> roomIds) {
        threadPoolTaskExecutor.execute(() -> {
            List<Map<String, Object>> roomsStatus = new ArrayList<>();
            roomIds.forEach(roomId -> {
                Map<String, Object> roomStatus = new HashMap<>();
                roomStatus.put("roomId", roomId);
                roomStatus.put("checkIn", TimeUtils.formatLocalDateTime(checkIn, TimeUtils.DATE_TIME_PATTERN));
                roomStatus.put("checkOut", TimeUtils.formatLocalDateTime(checkOut, TimeUtils.DATE_TIME_PATTERN));
                roomStatus.put("status", status);
                roomsStatus.add(roomStatus);
            });
            socketClientService.sendCommand(SocketConstants.Room.CLIENT, SocketConstants.Event.ROOMS_STATUS,
                    roomsStatus);
        });
    }
}
