package com.hit.hotel.repository.notification.repository;

import com.hit.hotel.repository.notification.entity.NotificationTemplate;
import com.hit.jpa.BaseJPARepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationTemplateRepository extends BaseJPARepository<NotificationTemplate, Integer> {

    @Query("select n from NotificationTemplate n where n.code = ?1")
    NotificationTemplate getNotificationTemplateByCode(String code);

}
