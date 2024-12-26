package com.hit.cache.config;

import com.hit.cache.config.properties.ExternalCacheConfigProperties;
import com.hit.cache.config.serializer.RedisSerializer;
import com.hit.cache.config.serializer.RedisSerializerImpl;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.api.RedissonRxClient;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@Configuration
@ConditionalOnProperty(
        value = {"app.external-cache.enable"},
        havingValue = "true"
)
public class RedisCacheConfig {

    @Bean
    @Primary
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        return new StringRedisTemplate(redisConnectionFactory);
    }

    @Bean
    @Primary
    public RedisSerializer myRedisSerializer() {
        return new RedisSerializerImpl();
    }

    @Bean
    public CacheManager redisCacheManager(
            RedissonConnectionFactory redissonConnectionFactory,
            ExternalCacheConfigProperties properties) {
        Map<String, RedisCacheConfiguration> config = new HashMap<>();
        for (Map.Entry<String, Long> cachesWithTtl : properties.getCacheExpirations().entrySet()) {
            config.put(cachesWithTtl.getKey(), this.createDefaultCacheConfiguration(properties,
                    cachesWithTtl.getValue()));
        }
        return RedisCacheManager
                .builder(redissonConnectionFactory)
                .cacheDefaults(this.createDefaultCacheConfiguration(properties, properties.getCacheDefaultExpire()))
                .withInitialCacheConfigurations(config).build();
    }

    public RedisCacheConfiguration createDefaultCacheConfiguration(ExternalCacheConfigProperties properties, long ttl) {
        return RedisCacheConfiguration.defaultCacheConfig()
                .serializeValuesWith(this.getJdkSerialization())
                .computePrefixWith(cacheName -> {
                    String prefix = properties.getApplicationCache() + properties.getDelimiter();
                    if (!cacheName.isEmpty()) {
                        prefix += cacheName + properties.getDelimiter();
                    }
                    return prefix;
                })
                .entryTtl(Duration.ofSeconds(ttl));
    }

    public RedisSerializationContext.SerializationPair<Object> getJdkSerialization() {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        JdkSerializationRedisSerializer jdkSerializer = new JdkSerializationRedisSerializer(loader);
        return RedisSerializationContext.SerializationPair.fromSerializer(jdkSerializer);
    }

    @Bean
    public RedissonConnectionFactory redissonConnectionFactory(RedissonClient redisson) {
        return new RedissonConnectionFactory(redisson);
    }

    @Lazy
    @Bean
    public RedissonRxClient redissonRxJava(RedissonClient redisson) {
        return redisson.rxJava();
    }

    @SneakyThrows
    @Bean(destroyMethod = "shutdown")
    public RedissonClient redissonClient(ExternalCacheConfigProperties properties) {
        log.info("Create RedissonClient");
        return Redisson.create(properties.getRedisson());
    }
}