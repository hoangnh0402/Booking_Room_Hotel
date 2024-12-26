package com.hit.hotel.auth.core.domain.request;

import com.hit.common.annotation.ValidFileImage;
import com.hit.common.core.constants.ErrorMessage;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateRequest {

    @NotNull(message = ErrorMessage.INVALID_SOME_THING_FIELD_IS_REQUIRED)
    @Email(message = ErrorMessage.INVALID_FORMAT_SOME_THING_FIELD)
    private String email;

    @NotEmpty(message = ErrorMessage.NOT_EMPTY_FIELD)
    @Pattern(regexp = "^\\d{9,11}$", message = ErrorMessage.INVALID_SOME_THING_FIELD)
    private String phoneNumber;

    @NotEmpty(message = ErrorMessage.NOT_EMPTY_FIELD)
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=\\S+$).{8,}$", message = ErrorMessage.INVALID_FORMAT_PASSWORD)
    private String password;

    @NotEmpty(message = ErrorMessage.NOT_EMPTY_FIELD)
    private String firstName;

    @NotEmpty(message = ErrorMessage.NOT_EMPTY_FIELD)
    private String lastName;

    @NotEmpty(message = ErrorMessage.NOT_EMPTY_FIELD)
    @Pattern(regexp = "^(Female)|(Male)$", message = ErrorMessage.INVALID_SOME_THING_FIELD)
    private String gender;

    @NotNull(message = ErrorMessage.INVALID_SOME_THING_FIELD_IS_REQUIRED)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthday;

    @NotEmpty(message = ErrorMessage.NOT_EMPTY_FIELD)
    private String address;

    @ValidFileImage
    private MultipartFile avatarFile;

}
