package com.hit.cache.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties("app.internal-cache")
public class InternalCacheConfigProperties {

    private Boolean enable;
    private CaffeineCacheConfig caffeine;

    @Setter
    @Getter
    public static class CaffeineCacheConfig {
        private String spec;
    }
}