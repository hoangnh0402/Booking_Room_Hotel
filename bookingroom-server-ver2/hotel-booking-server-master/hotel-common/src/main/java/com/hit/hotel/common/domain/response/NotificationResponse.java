package com.hit.hotel.common.domain.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponse {

    private Long id;

    private String title;

    private String content;

    private String path;

    private Boolean isRead = Boolean.FALSE;

    private String createdDate;
}
