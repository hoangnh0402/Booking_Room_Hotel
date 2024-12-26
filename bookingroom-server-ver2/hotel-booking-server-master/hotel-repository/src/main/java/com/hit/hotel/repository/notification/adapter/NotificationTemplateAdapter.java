package com.hit.hotel.repository.notification.adapter;

import com.hit.hotel.repository.notification.NotificationTemplateStore;
import com.hit.hotel.repository.notification.entity.NotificationTemplate;
import com.hit.hotel.repository.notification.repository.NotificationTemplateRepository;
import com.hit.jpa.BaseJPAAdapter;
import org.springframework.stereotype.Component;

@Component
public class NotificationTemplateAdapter
        extends BaseJPAAdapter<NotificationTemplate, Integer, NotificationTemplateRepository>
        implements NotificationTemplateStore {

    @Override
    protected Class<NotificationTemplate> getEntityClass() {
        return NotificationTemplate.class;
    }

    @Override
    public NotificationTemplate getByCode(String code) {
        return this.repository.getNotificationTemplateByCode(code);
    }
}
