package com.hit.hotel.repository.notification.repository;

import com.hit.hotel.repository.notification.entity.Notification;
import com.hit.jpa.BaseJPARepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface NotificationRepository extends BaseJPARepository<Notification, Long> {

    @Query("SELECT count(n) FROM Notification n WHERE n.isRead = ?1")
    Integer countByIsRead(Boolean isRead);

    @Query("SELECT n FROM Notification n")
    Page<Notification> getNotifications(Pageable pageable);

    @Modifying
    @Transactional
    @Query("UPDATE Notification SET isRead = true WHERE isRead = false")
    void readAllNotification();

}
