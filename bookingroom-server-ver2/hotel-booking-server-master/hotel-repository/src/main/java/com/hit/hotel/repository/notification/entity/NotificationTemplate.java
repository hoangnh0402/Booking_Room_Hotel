package com.hit.hotel.repository.notification.entity;

import com.hit.hotel.repository.base.DateAuditing;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "notification_template")
@Entity(name = "NotificationTemplate")
public class NotificationTemplate extends DateAuditing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content_template", nullable = false)
    private String contentTemplate;

    @Column(name = "path_template")
    private String pathTemplate;
}
