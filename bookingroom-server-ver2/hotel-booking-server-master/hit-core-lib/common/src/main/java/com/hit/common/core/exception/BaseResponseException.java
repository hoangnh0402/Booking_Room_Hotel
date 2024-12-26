package com.hit.common.core.exception;

import com.hit.common.core.domain.model.ResponseStatus;
import com.hit.common.core.domain.model.ResponseStatusCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BaseResponseException extends RuntimeException {

    private final transient ResponseStatusCode responseStatusCode;
    private final ResponseStatus responseStatus;
    private final String[] params;

    public BaseResponseException(ResponseStatusCode responseStatusCode) {
        this.responseStatusCode = responseStatusCode;
        this.responseStatus = null;
        this.params = null;
    }

    public BaseResponseException(ResponseStatusCode responseStatusCode, String[] params) {
        this.responseStatusCode = responseStatusCode;
        this.responseStatus = null;
        this.params = params;
    }

    public BaseResponseException(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
        this.responseStatusCode = null;
        this.params = null;
    }
}
