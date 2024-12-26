package com.hit.common.core.domain.model;

import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Builder
public record ResponseStatusCode(String code, HttpStatus httpStatus) implements Serializable {

}
