package com.hit.common.utils;

import lombok.experimental.UtilityClass;

import java.util.*;
import java.util.function.Function;

@UtilityClass
public class CollectionUtils {

    public <T> List<T> paging(List<T> data, Integer page, Integer pageSize) {
        int totalPage = (int) Math.ceil((double) data.size() / pageSize);
        if (totalPage <= page) {
            return new ArrayList<>();
        }
        if ((page + 1) * pageSize >= data.size()) {
            return data.subList(page * pageSize, data.size());
        }
        return data.subList(page * pageSize, (page + 1) * pageSize);
    }

    public static <K, V> Map<K, V> toMap(Iterable<V> values, Function<? super V, K> keyFunction) {
        return toMap(values.iterator(), keyFunction);
    }

    public static <K, V> Map<K, V> toMap(Iterator<V> values, Function<? super V, K> keyFunction) {
        Map<K, V> result = new LinkedHashMap<>();
        while (values.hasNext()) {
            V value = values.next();
            result.put(keyFunction.apply(value), value);
        }
        return new HashMap<>(result);
    }

    public static <K, V> Map<K, V> toMap(V[] values, Function<? super V, K> keyFunction) {
        Map<K, V> result = new LinkedHashMap<>();
        for (V value : values) {
            result.put(keyFunction.apply(value), value);
        }
        return new HashMap<>(result);
    }

}
