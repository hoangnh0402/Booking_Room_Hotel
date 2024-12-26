package com.hit.hotel.auth.ui.rest;

import com.hit.api.config.annotation.UserPrincipalRequest;
import com.hit.api.factory.GeneralResponse;
import com.hit.common.core.constants.ErrorMessage;
import com.hit.common.core.domain.model.UserPrincipal;
import com.hit.common.core.domain.response.CommonResponse;
import com.hit.hotel.auth.core.domain.request.UserCreateRequest;
import com.hit.hotel.auth.core.domain.request.UserLoginRequest;
import com.hit.hotel.auth.core.domain.response.UserLoginResponse;
import com.hit.hotel.auth.core.domain.response.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RequestMapping("${app.auth-api-path-prefix}/api/v1/auth")
public interface AuthOperations {

    @Operation(summary = "API Login")
    @PostMapping("/login")
    ResponseEntity<GeneralResponse<UserLoginResponse>> login(@Valid @RequestBody UserLoginRequest request);

    @Operation(summary = "API logout")
    @PostMapping("/logout")
    ResponseEntity<GeneralResponse<CommonResponse>> logout(HttpServletRequest request);

    @Operation(summary = "API get me")
    @GetMapping("/me")
    ResponseEntity<GeneralResponse<UserResponse>> me(@Parameter(name = "userPrincipal", hidden = true)
                                                     @UserPrincipalRequest UserPrincipal userPrincipal);

    @Operation(summary = "API create signUp and send mail token (1)")
    @PostMapping(value = "/signup", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<GeneralResponse<CommonResponse>> signUp(@Valid @ModelAttribute UserCreateRequest request);

    @Operation(summary = "API verify signup (2)")
    @PostMapping("/signup/verify")
    ResponseEntity<GeneralResponse<CommonResponse>> verifySignUp(@RequestParam String email,
                                                                 @RequestParam String token);

    @Operation(summary = "API create forgot password (1)")
    @PostMapping("/forgot-password")
    ResponseEntity<GeneralResponse<CommonResponse>> forgotPassword(
            @Email(message = ErrorMessage.INVALID_FORMAT_SOME_THING_FIELD)
            @RequestParam String email);

    @Operation(summary = "API verify forgot password (2)")
    @PostMapping("/forgot-password/verify")
    ResponseEntity<GeneralResponse<CommonResponse>> verifyForgotPassword(
            @Email(message = ErrorMessage.INVALID_FORMAT_SOME_THING_FIELD)
            @RequestParam String email,
            @RequestParam String token,
            @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=\\S+$).{8,}$", message = ErrorMessage.INVALID_FORMAT_PASSWORD)
            @RequestParam("password") String newPassword);

}
