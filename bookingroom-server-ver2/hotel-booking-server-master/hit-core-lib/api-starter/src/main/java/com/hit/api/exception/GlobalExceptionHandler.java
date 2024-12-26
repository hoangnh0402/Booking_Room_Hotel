package com.hit.api.exception;

import com.hit.api.factory.GeneralResponse;
import com.hit.api.factory.ResponseFactory;
import com.hit.common.core.domain.model.ResponseStatus;
import com.hit.common.core.exception.BaseResponseException;
import com.hit.common.core.exception.BusinessException;
import com.hit.common.core.exception.ResponseStatusCodeEnum;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ObjectUtils;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Log4j2
@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {

    //Error validate for param
    @ExceptionHandler(ConstraintViolationException.class)
    @org.springframework.web.bind.annotation.ResponseStatus(HttpStatus.BAD_REQUEST)
    public <T> GeneralResponse<T> handleValidatedException(ConstraintViolationException ex) {
        GeneralResponse<T> response = new GeneralResponse<>();
        Map<String, String> result = new LinkedHashMap<>();
        ex.getConstraintViolations().forEach(error -> {
            String fieldName = ((PathImpl) error.getPropertyPath()).getLeafNode().getName();
            result.put(fieldName, error.getMessage());
        });
        ResponseStatus responseStatus = new ResponseStatus(ResponseStatusCodeEnum.VALIDATION_ERROR.code(), result);
        response.setStatus(responseStatus);
        return response;
    }

    //Error validate for body
    @ExceptionHandler(BindException.class)
    @org.springframework.web.bind.annotation.ResponseStatus(HttpStatus.BAD_REQUEST)
    public <T> GeneralResponse<T> handleValidException(BindException ex) {
        GeneralResponse<T> response = new GeneralResponse<>();
        Map<String, String> result = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            result.put(fieldName, error.getDefaultMessage());
        });
        ResponseStatus responseStatus = new ResponseStatus(ResponseStatusCodeEnum.VALIDATION_ERROR.code(), result);
        response.setStatus(responseStatus);
        return response;
    }

    @ExceptionHandler({ServletRequestBindingException.class})
    @org.springframework.web.bind.annotation.ResponseStatus(HttpStatus.BAD_REQUEST)
    public <T> GeneralResponse<T> handleValidationExceptions(ServletRequestBindingException ex) {
        GeneralResponse<T> response = new GeneralResponse<>();
        ResponseStatus responseStatus = new ResponseStatus(ResponseStatusCodeEnum.VALIDATION_ERROR.code(),
                ex.getMessage());
        response.setStatus(responseStatus);
        return response;
    }

    @ExceptionHandler(Exception.class)
    @org.springframework.web.bind.annotation.ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<GeneralResponse<Object>> handlerException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return ResponseFactory.error(ResponseStatusCodeEnum.INTERNAL_GENERAL_SERVER_ERROR);
    }

    //Exception custom
    @ExceptionHandler({BaseResponseException.class})
    public ResponseEntity<GeneralResponse<Object>> handleBaseResponseException(BaseResponseException ex) {
        if (ObjectUtils.isNotEmpty(ex.getResponseStatus())) {
            return ResponseFactory.error(ex.getResponseStatusCode());
        } else {
            if (ObjectUtils.isEmpty(ex.getParams())) {
                return ResponseFactory.error(ex.getResponseStatusCode());
            } else {
                return ResponseFactory.error(ex.getResponseStatusCode(), ex.getParams());
            }
        }
    }

    @ExceptionHandler({BusinessException.class})
    public ResponseEntity<GeneralResponse<Object>> handleBusinessException(BusinessException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseFactory.error(ResponseStatusCodeEnum.BUSINESS_ERROR);
    }
}
