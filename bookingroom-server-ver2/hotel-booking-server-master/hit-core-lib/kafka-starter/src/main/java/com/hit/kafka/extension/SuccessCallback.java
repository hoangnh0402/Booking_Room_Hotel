package com.hit.kafka.extension;

import org.springframework.lang.Nullable;

@FunctionalInterface
public interface SuccessCallback<T> {
    void onSuccess(@Nullable T result);
}
