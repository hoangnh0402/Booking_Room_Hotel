package com.hit.hotel.auth.ui.rest.controller;

import com.hit.api.factory.GeneralResponse;
import com.hit.api.factory.ResponseFactory;
import com.hit.common.core.domain.model.UserPrincipal;
import com.hit.common.core.domain.response.CommonResponse;
import com.hit.hotel.auth.core.domain.request.UserCreateRequest;
import com.hit.hotel.auth.core.domain.request.UserLoginRequest;
import com.hit.hotel.auth.core.domain.response.UserLoginResponse;
import com.hit.hotel.auth.core.domain.response.UserResponse;
import com.hit.hotel.auth.core.service.AuthService;
import com.hit.hotel.auth.ui.rest.AuthOperations;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthOperations {

    private final AuthService authService;

    @Override
    public ResponseEntity<GeneralResponse<UserLoginResponse>> login(UserLoginRequest request) {
        return ResponseFactory.success(authService.login(request));
    }

    @Override
    public ResponseEntity<GeneralResponse<CommonResponse>> logout(HttpServletRequest request) {
        return ResponseFactory.success(authService.logout(request));
    }

    @Override
    public ResponseEntity<GeneralResponse<UserResponse>> me(UserPrincipal userPrincipal) {
        return ResponseFactory.success(authService.me(userPrincipal));
    }

    @Override
    public ResponseEntity<GeneralResponse<CommonResponse>> signUp(UserCreateRequest request) {
        return ResponseFactory.success(authService.signUp(request));
    }

    @Override
    public ResponseEntity<GeneralResponse<CommonResponse>> verifySignUp(String email, String token) {
        return ResponseFactory.success(authService.verifySignUp(email, token));
    }

    @Override
    public ResponseEntity<GeneralResponse<CommonResponse>> forgotPassword(String email) {
        return ResponseFactory.success(authService.forgotPassword(email));
    }

    @Override
    public ResponseEntity<GeneralResponse<CommonResponse>> verifyForgotPassword(String email, String token,
                                                                                String newPassword) {
        return ResponseFactory.success(authService.verifyForgotPassword(email, token, newPassword));
    }

}
