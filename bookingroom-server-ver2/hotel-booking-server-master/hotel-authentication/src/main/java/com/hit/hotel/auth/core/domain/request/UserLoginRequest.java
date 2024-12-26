package com.hit.hotel.auth.core.domain.request;

import com.hit.common.core.constants.ErrorMessage;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginRequest {

    @NotEmpty(message = ErrorMessage.NOT_EMPTY_FIELD)
    private String emailOrPhone;

    @NotEmpty(message = ErrorMessage.NOT_EMPTY_FIELD)
    private String password;

}
