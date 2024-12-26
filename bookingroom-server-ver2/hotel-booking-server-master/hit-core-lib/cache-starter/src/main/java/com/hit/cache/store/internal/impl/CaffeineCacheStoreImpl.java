package com.hit.cache.store.internal.impl;

import com.hit.cache.store.internal.InternalCacheStore;
import com.hit.cache.store.internal.RxInternalCacheStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.function.Function;

@Log4j2
@Primary
@RequiredArgsConstructor
@Service("CaffeineCacheStore")
@ConditionalOnBean(name = "caffeineCacheManager")
public class CaffeineCacheStoreImpl implements RxInternalCacheStore, InternalCacheStore {

    @Qualifier("caffeineCacheManager")
    private final CacheManager caffeineCacheManager;

    @Override
    public void putAllBlocking(String cacheName, Map<Object, Object> data) {
        Cache cache = caffeineCacheManager.getCache(cacheName);
        if (cache != null) data.forEach(cache::put);
    }

    @Override
    public void putBlocking(String cacheName, Object k, Object v) {
        Cache cache = caffeineCacheManager.getCache(cacheName);
        if (cache != null) cache.put(k, v);
    }

    @Override
    public <T> T getBlocking(String cacheName, Object key, Class<T> type) {
        Cache cache = caffeineCacheManager.getCache(cacheName);
        if (cache != null) return cache.get(key, type);
        return null;
    }

    @Override
    public <T, R> R getBlocking(String cacheName, Object key, Class<T> type,
                                Function<? super T, ? extends R> handleCache) {
        Cache cache = caffeineCacheManager.getCache(cacheName);
        return handleCache.apply(cache != null ? cache.get(key, type) : null);
    }

    @Override
    public <T, R> R getAndPutBlocking(String cacheName, Object key, Class<T> type,
                                      Function<? super T, ? extends R> handleCache) {
        Cache cache = caffeineCacheManager.getCache(cacheName);
        T cacheValue = cache != null ? cache.get(key, type) : null;
        R value = handleCache.apply(cacheValue);
        if (value != null) this.putBlocking(cacheName, key, value);
        return value;
    }

    @Override
    public void clearCacheBlocking(String cacheName) {
        Cache cache = caffeineCacheManager.getCache(cacheName);
        if (cache != null) cache.clear();
    }
}
