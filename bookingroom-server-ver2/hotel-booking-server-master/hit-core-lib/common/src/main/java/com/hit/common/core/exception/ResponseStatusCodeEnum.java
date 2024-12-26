package com.hit.common.core.exception;

import com.hit.common.core.domain.model.ResponseStatusCode;
import org.springframework.http.HttpStatus;

public interface ResponseStatusCodeEnum {
    ResponseStatusCode SUCCESS = ResponseStatusCode.builder().code("00").httpStatus(HttpStatus.OK).build();
    ResponseStatusCode INTERNAL_GENERAL_SERVER_ERROR = ResponseStatusCode.builder().code("GENERAL_SERVER_ERROR").httpStatus(HttpStatus.INTERNAL_SERVER_ERROR).build();
    ResponseStatusCode BUSINESS_ERROR = ResponseStatusCode.builder().code("BUSINESS_ERROR").httpStatus(HttpStatus.BAD_REQUEST).build();
    ResponseStatusCode VALIDATION_ERROR = ResponseStatusCode.builder().code("VALIDATION_ERROR").httpStatus(HttpStatus.BAD_REQUEST).build();
    ResponseStatusCode RESOURCE_NOT_FOUND = ResponseStatusCode.builder().code("RESOURCE_NOT_FOUND").httpStatus(HttpStatus.BAD_REQUEST).build();

    //Auth
    ResponseStatusCode UNAUTHORIZED_ERROR = ResponseStatusCode.builder().code("AUTH001").httpStatus(HttpStatus.UNAUTHORIZED).build();
    ResponseStatusCode FORBIDDEN_ERROR = ResponseStatusCode.builder().code("AUTH002").httpStatus(HttpStatus.FORBIDDEN).build();
    ResponseStatusCode NOT_PERMISSION_DELETE_UPDATE = ResponseStatusCode.builder().code("AUTH003").httpStatus(HttpStatus.FORBIDDEN).build();
    ResponseStatusCode INCORRECT_EMAIL = ResponseStatusCode.builder().code("AUTH004").httpStatus(HttpStatus.BAD_REQUEST).build();
    ResponseStatusCode INCORRECT_EMAIL_OR_PHONE = ResponseStatusCode.builder().code("AUTH005").httpStatus(HttpStatus.BAD_REQUEST).build();
    ResponseStatusCode PASSWORD_INCORRECT = ResponseStatusCode.builder().code("AUTH006").httpStatus(HttpStatus.BAD_REQUEST).build();
    ResponseStatusCode ACCOUNT_LOCKED = ResponseStatusCode.builder().code("AUTH007").httpStatus(HttpStatus.LOCKED).build();
    ResponseStatusCode ACCOUNT_NOT_ENABLED = ResponseStatusCode.builder().code("AUTH008").httpStatus(HttpStatus.LOCKED).build();
    ResponseStatusCode INVALID_TOKEN = ResponseStatusCode.builder().code("AUTH009").httpStatus(HttpStatus.BAD_REQUEST).build();
    ResponseStatusCode EXPIRED_TOKEN = ResponseStatusCode.builder().code("AUTH010").httpStatus(HttpStatus.BAD_REQUEST).build();
    ResponseStatusCode INVALID_REFRESH_TOKEN = ResponseStatusCode.builder().code("AUTH011").httpStatus(HttpStatus.BAD_REQUEST).build();
    ResponseStatusCode EMAIL_OR_PHONE_REGISTERED = ResponseStatusCode.builder().code("AUTH012").httpStatus(HttpStatus.BAD_REQUEST).build();
    ResponseStatusCode NEW_PASSWORD_MATCHES_OLD_PASSWORD = ResponseStatusCode.builder().code("AUTH013").httpStatus(HttpStatus.BAD_REQUEST).build();

    // Core
    ResponseStatusCode ROOM_NOT_FOUND = ResponseStatusCode.builder().code("CORE001").httpStatus(HttpStatus.BAD_REQUEST).build();
    ResponseStatusCode ROOM_RATING_NOT_FOUND = ResponseStatusCode.builder().code("CORE002").httpStatus(HttpStatus.BAD_REQUEST).build();
    ResponseStatusCode NOT_PERMISSION_UPDATE_RATING = ResponseStatusCode.builder().code("CORE003").httpStatus(HttpStatus.BAD_REQUEST).build();
    ResponseStatusCode NOT_PERMISSION_DELETE_RATING = ResponseStatusCode.builder().code("CORE004").httpStatus(HttpStatus.BAD_REQUEST).build();
    ResponseStatusCode INVALID_DATE_CHECK_IN = ResponseStatusCode.builder().code("CORE005").httpStatus(HttpStatus.BAD_REQUEST).build();
    ResponseStatusCode INVALID_DATE_CHECK_OUT = ResponseStatusCode.builder().code("CORE006").httpStatus(HttpStatus.BAD_REQUEST).build();
    ResponseStatusCode ROOMS_NOT_FOUND = ResponseStatusCode.builder().code("CORE007").httpStatus(HttpStatus.BAD_REQUEST).build();
    ResponseStatusCode ROOMS_UNAVAILABLE = ResponseStatusCode.builder().code("CORE008").httpStatus(HttpStatus.BAD_REQUEST).build();
    ResponseStatusCode SERVICE_NOT_FOUND = ResponseStatusCode.builder().code("CORE009").httpStatus(HttpStatus.BAD_REQUEST).build();
    ResponseStatusCode SERVICES_NOT_FOUND = ResponseStatusCode.builder().code("CORE010").httpStatus(HttpStatus.BAD_REQUEST).build();
    ResponseStatusCode PRODUCT_NOT_FOUND = ResponseStatusCode.builder().code("CORE011").httpStatus(HttpStatus.BAD_REQUEST).build();
    ResponseStatusCode SALE_NOT_FOUND = ResponseStatusCode.builder().code("CORE012").httpStatus(HttpStatus.BAD_REQUEST).build();
    ResponseStatusCode USER_NOT_FOUND = ResponseStatusCode.builder().code("CORE013").httpStatus(HttpStatus.BAD_REQUEST).build();
    ResponseStatusCode USER_UNLOCKED_NOT_FOUND = ResponseStatusCode.builder().code("CORE014").httpStatus(HttpStatus.BAD_REQUEST).build();
    ResponseStatusCode BOOKING_NOT_FOUND = ResponseStatusCode.builder().code("CORE015").httpStatus(HttpStatus.BAD_REQUEST).build();
    ResponseStatusCode CAN_NOT_CANCEL_BOOKING = ResponseStatusCode.builder().code("CORE016").httpStatus(HttpStatus.BAD_REQUEST).build();
    ResponseStatusCode ROOM_ON_SALE = ResponseStatusCode.builder().code("CORE017").httpStatus(HttpStatus.BAD_REQUEST).build();

}
