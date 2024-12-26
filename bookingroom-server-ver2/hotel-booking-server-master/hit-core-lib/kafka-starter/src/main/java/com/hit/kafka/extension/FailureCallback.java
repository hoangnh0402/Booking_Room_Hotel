package com.hit.kafka.extension;

@FunctionalInterface
public interface FailureCallback {
    void onFailure(Throwable ex);
}
