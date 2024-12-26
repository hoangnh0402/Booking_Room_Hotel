package com.hit.hotel.auth.core.service;


import com.hit.common.core.domain.model.UserPrincipal;
import com.hit.common.core.domain.response.CommonResponse;
import com.hit.hotel.auth.core.domain.request.UserCreateRequest;
import com.hit.hotel.auth.core.domain.request.UserLoginRequest;
import com.hit.hotel.auth.core.domain.response.UserLoginResponse;
import com.hit.hotel.auth.core.domain.response.UserResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {

    UserLoginResponse login(UserLoginRequest request);

    CommonResponse logout(HttpServletRequest request);

    UserResponse me(UserPrincipal userPrincipal);

    CommonResponse signUp(UserCreateRequest request);

    CommonResponse verifySignUp(String email, String token);

    CommonResponse forgotPassword(String email);

    CommonResponse verifyForgotPassword(String email, String token, String newPassword);

}
