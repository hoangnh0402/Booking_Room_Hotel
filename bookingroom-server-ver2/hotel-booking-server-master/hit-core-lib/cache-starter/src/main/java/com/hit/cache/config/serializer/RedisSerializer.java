package com.hit.cache.config.serializer;

public interface RedisSerializer {

    <R> String serializer(R value);

    <R> byte[] serializerRaw(R value);

    <R> R deserializer(String value, Class<R> type);

    <R> R deserializerRaw(byte[] raw, Class<R> type);

}
