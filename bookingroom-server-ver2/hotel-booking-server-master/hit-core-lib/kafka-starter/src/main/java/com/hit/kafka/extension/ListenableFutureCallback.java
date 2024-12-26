package com.hit.kafka.extension;

public interface ListenableFutureCallback<T> extends SuccessCallback<T>, FailureCallback {
}
