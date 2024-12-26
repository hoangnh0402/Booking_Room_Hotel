package com.hit.hotel.core.config;

import com.hit.api.utils.SecurityUtils;
import com.hit.jpa.AuditorProvider;
import org.springframework.context.annotation.*;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.Objects;

@Configuration
public class AuditorCoreConfig {

    @Bean("auditorProvider")
    @Conditional(OnMissingAuditorProviderBeanCondition.class)
    public AuditorProvider auditorProvider() {
        return () -> SecurityUtils.getUserId(SecurityUtils.getCurrentAuthentication());
    }

    public static class OnMissingAuditorProviderBeanCondition implements Condition {
        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            return !Objects.requireNonNull(context.getBeanFactory()).containsBean("auditorProvider");
        }
    }

}
