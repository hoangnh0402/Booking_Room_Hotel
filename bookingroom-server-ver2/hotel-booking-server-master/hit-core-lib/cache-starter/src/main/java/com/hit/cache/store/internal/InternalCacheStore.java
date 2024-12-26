package com.hit.cache.store.internal;

import java.util.Map;
import java.util.function.Function;

public interface InternalCacheStore {

    void putAllBlocking(String cacheName, Map<Object, Object> data);

    void putBlocking(String cacheName, Object k, Object v);

    <T> T getBlocking(String cacheName, Object key, Class<T> type);

    <T, R> R getBlocking(String cacheName, Object key, Class<T> type,
                         Function<? super T, ? extends R> handleCache);

    <T, R> R getAndPutBlocking(String cacheName, Object key, Class<T> type,
                               Function<? super T, ? extends R> handleCache);

    void clearCacheBlocking(String cacheName);

}
