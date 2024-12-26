package com.hit.cache.config.serializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.SneakyThrows;

import java.nio.charset.StandardCharsets;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static com.fasterxml.jackson.databind.PropertyNamingStrategies.LOWER_CAMEL_CASE;

public class RedisSerializerImpl implements RedisSerializer {

    private static ObjectMapper objectMapper;

    private static ObjectMapper getObjectMapper() {
        if (objectMapper == null) return jsonConfig();
        return objectMapper;
    }

    private static ObjectMapper jsonConfig() {
        objectMapper = new ObjectMapper();
        objectMapper
                .registerModule(new JavaTimeModule())
                .setPropertyNamingStrategy(LOWER_CAMEL_CASE)
                .configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }

    @Override
    public <R> String serializer(R value) {
        return this.toJsonString(value);
    }

    @Override
    public <R> byte[] serializerRaw(R key) {
        return this.toJsonBytes(key);
    }

    @Override
    @SneakyThrows
    public <R> R deserializer(String value, Class<R> type) {
        if (value == null || value.isEmpty()) {
            return null;
        } else {
            return getObjectMapper().readValue(value, type);
        }
    }

    @Override
    @SneakyThrows
    public <R> R deserializerRaw(byte[] key, Class<R> type) {
        return getObjectMapper().readValue(key, type);
    }

    @SneakyThrows
    private <R> byte[] toJsonBytes(R data) {
        if (data instanceof String string) {
            return string.getBytes(StandardCharsets.UTF_8);
        } else {
            return getObjectMapper().writeValueAsBytes(data);
        }
    }

    @SneakyThrows
    private <R> String toJsonString(R data) {
        if (data instanceof String string) {
            return string;
        } else {
            return getObjectMapper().writeValueAsString(data);
        }
    }

}
