package com.hit.api.factory;

import com.hit.common.core.domain.model.ResponseStatus;
import com.hit.common.core.exception.ResponseStatusCodeEnum;
import com.hit.common.core.json.JsonMapper;
import com.hit.common.core.domain.model.ResponseStatusCode;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.util.MultiValueMap;

import java.io.IOException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseFactory {

    public static <T> ResponseEntity<GeneralResponse<T>> success(T data) {
        return success(HttpStatus.OK, data);
    }

    public static <T> ResponseEntity<GeneralResponse<T>> success(HttpStatus httpStatus, T data) {
        ResponseStatus responseStatus = parseResponseStatus(ResponseStatusCodeEnum.SUCCESS.code(), null);
        GeneralResponse<T> generalResponse = new GeneralResponse<>(responseStatus, data);
        return ResponseEntity.status(httpStatus).body(generalResponse);
    }

    public static <T> ResponseEntity<GeneralResponse<T>> success(MultiValueMap<String, String> headers, T data) {
        return success(HttpStatus.OK, headers, data);
    }

    public static <T> ResponseEntity<GeneralResponse<T>> success(HttpStatus httpStatus, MultiValueMap<String, String> headers, T data) {
        ResponseStatus responseStatus = parseResponseStatus(ResponseStatusCodeEnum.SUCCESS.code(), null);
        GeneralResponse<T> generalResponse = new GeneralResponse<>(responseStatus, data);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.addAll(headers);
        return ResponseEntity.status(httpStatus).headers(responseHeaders).body(generalResponse);
    }

    public static ResponseEntity<GeneralResponse<Object>> error(ResponseStatusCode responseStatusCode) {
        return error(responseStatusCode, null);
    }

    public static ResponseEntity<GeneralResponse<Object>> error(ResponseStatusCode responseStatusCode, String[] params) {
        ResponseStatus responseStatus = parseResponseStatus(responseStatusCode.code(), params);
        GeneralResponse<Object> generalResponse = new GeneralResponse<>();
        generalResponse.setStatus(responseStatus);
        return ResponseEntity.status(responseStatusCode.httpStatus()).body(generalResponse);
    }

    public static ResponseEntity<GeneralResponse<Object>> error(ResponseStatus responseStatus) {
        GeneralResponse<Object> generalResponse = new GeneralResponse<>();
        generalResponse.setStatus(responseStatus);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(generalResponse);
    }

    public static void httpServletResponseToClient(HttpServletResponse response, ResponseStatusCode responseStatusCode) throws IOException {
        ResponseStatus responseStatus = parseResponseStatus(responseStatusCode.code(), null);
        GeneralResponse<Object> generalResponse = new GeneralResponse<>();
        generalResponse.setStatus(responseStatus);
        try (ServletServerHttpResponse serverHttpResponse = new ServletServerHttpResponse(response)) {
            serverHttpResponse.setStatusCode(responseStatusCode.httpStatus());
            serverHttpResponse.getServletResponse().setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            serverHttpResponse.getBody().write(JsonMapper.encodeAsByte(generalResponse));
        }
    }

    private static ResponseStatus parseResponseStatus(String code, String[] params) {
        return new ResponseStatus(code, params, true);
    }
}
