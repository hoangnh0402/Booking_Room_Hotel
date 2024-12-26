package com.hit.cache.store.external;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public interface ExternalCacheStore {

    <T> void putObject(String key, T value);

    <T> void putObject(String key, T value, long expireSeconds);

    <T> T getObject(String key, Class<T> objectClass);

    <K extends Comparable<? super K>, V> void putObjectAsHash(String key, Map<K, V> value);

    <K extends Comparable<? super K>, V> void putObjectAsHash(String key, Map<K, V> value, long expireSeconds);

    <K extends Comparable<? super K>, V> void putObjectToHash(String key, K hashKey, V hashValue);

    <K extends Comparable<? super K>, V> Map<K, V> getObjectAsHash(String key, Class<K> objectClassKey,
                                                                   Class<V> objectClassValue);

    <K extends Comparable<? super K>, V> V getObjectFromHash(String key, K hashKey, Class<V> objectClassValue);

    <K extends Comparable<? super K>> void deleteHashValue(String key, K... hashKeys);

    void delete(String key);

    boolean hasKey(String key);

    void watchKey(String key);

    <T> T lockAndHandle(List<String> keyLocks, Supplier<T> handler);

}
