package com.hit.api.factory;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hit.common.core.domain.model.ResponseStatus;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GeneralResponse<T> {

    @JsonProperty("status")
    private ResponseStatus status;

    @JsonProperty("data")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public GeneralResponse(T data) {
        this.data = data;
    }
}
