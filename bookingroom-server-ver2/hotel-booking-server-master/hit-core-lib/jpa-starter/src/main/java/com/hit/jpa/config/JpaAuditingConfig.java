package com.hit.jpa.config;

import com.hit.jpa.AuditorProvider;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@Configuration
@ConditionalOnProperty(
        value = {"app.datasource.default.enable"},
        havingValue = "true"
)
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class JpaAuditingConfig {

    @Setter(onMethod_={@Autowired(required = false)})
    private AuditorProvider auditorProvider;

    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> Optional.of(Optional.ofNullable(auditorProvider == null ? null :
                auditorProvider.getAuditorProvider()).orElse("ANONYMOUS"));
    }
}
