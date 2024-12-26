package com.hit.hotel.auth.core.service.impl;

import com.hit.cache.store.external.ExternalCacheStore;
import com.hit.common.core.domain.model.UserPrincipal;
import com.hit.common.core.domain.request.SendMailRequest;
import com.hit.common.core.domain.response.CommonResponse;
import com.hit.common.core.exception.BaseResponseException;
import com.hit.common.core.exception.ResponseStatusCodeEnum;
import com.hit.common.core.jwt.JwtProvider;
import com.hit.common.core.domain.model.SimpleSecurityUser;
import com.hit.common.utils.DataUtils;
import com.hit.common.utils.SendMailUtils;
import com.hit.hotel.auth.core.constant.MailConstants;
import com.hit.hotel.auth.core.domain.mapper.UserMapper;
import com.hit.hotel.auth.core.domain.request.UserCreateRequest;
import com.hit.hotel.auth.core.domain.request.UserLoginRequest;
import com.hit.hotel.auth.core.domain.response.UserLoginResponse;
import com.hit.hotel.auth.core.domain.response.UserResponse;
import com.hit.hotel.auth.core.service.AuthService;
import com.hit.hotel.auth.core.service.UserService;
import com.hit.hotel.repository.user.UserStore;
import com.hit.hotel.repository.user.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;

    private final UserStore userStore;

    private final ExternalCacheStore externalCacheStore;

    private final JwtProvider jwtProvider;

    private final SendMailUtils sendMailUtils;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    private static final String VERIFY_TOKEN_SIGNUP_KEY = "VERIFY_TOKEN_SIGNUP::%s";
    private static final String VERIFY_TOKEN_FORGOT_PASSWORD_KEY = "VERIFY_TOKEN_FORGOT_PASSWORD::%s";

    @Override
    @Transactional
    public UserLoginResponse login(UserLoginRequest request) {
        User user = userStore.getUserByEmailOrPhone(request.getEmailOrPhone(), request.getEmailOrPhone());
        if (user == null) {
            throw new BaseResponseException(ResponseStatusCodeEnum.INCORRECT_EMAIL_OR_PHONE);
        }
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BaseResponseException(ResponseStatusCodeEnum.PASSWORD_INCORRECT);
        }
        List<String> authorities = List.of(user.getRole().getName());
        SimpleSecurityUser simpleSecurityUser = SimpleSecurityUser.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phone(user.getPhoneNumber())
                .authorities(authorities)
                .build();
        return new UserLoginResponse()
                .setAccessToken(jwtProvider.generateToken(simpleSecurityUser, Boolean.FALSE))
                .setRefreshToken(jwtProvider.generateToken(simpleSecurityUser, Boolean.TRUE))
                .setAuthorities(authorities);
    }

    @Override
    public CommonResponse logout(HttpServletRequest request) {
        // logic here
        return new CommonResponse().setStatus(Boolean.TRUE).setMessage("Logged out successfully");
    }

    @Override
    public UserResponse me(UserPrincipal userPrincipal) {
        User user = userStore.getUserByEmail(userPrincipal.getUser().getEmail());
        if (ObjectUtils.isEmpty(user)) {
            throw new BaseResponseException(ResponseStatusCodeEnum.RESOURCE_NOT_FOUND);
        }
        return userMapper.toUserResponse(user);
    }

    @Override
    public CommonResponse signUp(UserCreateRequest request) {
        User existsUser = userStore.getUserByEmailOrPhone(request.getEmail(), request.getPhoneNumber());
        if (ObjectUtils.isNotEmpty(existsUser)) {
            if (BooleanUtils.isTrue(existsUser.getIsEnable())) { // User dki nhung da kick hoat
                throw new BaseResponseException(ResponseStatusCodeEnum.EMAIL_OR_PHONE_REGISTERED);
            } else { // User dki nhung chua kick hoat
                String keyVerifyTokenSignUp = String.format(VERIFY_TOKEN_SIGNUP_KEY, request.getEmail());
                String verificationToken = this.saveVerificationToken(keyVerifyTokenSignUp);
                this.sendMailSignUp(request, verificationToken);
            }
        } else {
            userService.createUser(request);
            String keyVerifyTokenSignUp = String.format(VERIFY_TOKEN_SIGNUP_KEY, request.getPhoneNumber());
            String verificationToken = this.saveVerificationToken(keyVerifyTokenSignUp);
            this.sendMailSignUp(request, verificationToken);
        }
        return new CommonResponse().setStatus(Boolean.TRUE).setMessage("Create signup successfully");
    }

    @Override
    public CommonResponse verifySignUp(String email, String token) {
        String keyVerifyTokenSignUp = String.format(VERIFY_TOKEN_SIGNUP_KEY, email);
        this.validateVerifyToken(token, keyVerifyTokenSignUp);
        Integer enabled = userStore.enableUserByEmail(email);
        if (enabled < 1) {
            throw new BaseResponseException(ResponseStatusCodeEnum.INCORRECT_EMAIL);
        }
        return new CommonResponse().setStatus(Boolean.TRUE).setMessage("Signup successfully");
    }

    @Override
    public CommonResponse forgotPassword(String email) {
        User user = userStore.getUserByEmail(email);
        if (ObjectUtils.isEmpty(user)) {
            throw new BaseResponseException(ResponseStatusCodeEnum.INCORRECT_EMAIL);
        } else if (BooleanUtils.isFalse(user.getIsEnable())) {
            throw new BaseResponseException(ResponseStatusCodeEnum.ACCOUNT_NOT_ENABLED);
        } else if (BooleanUtils.isTrue(user.getIsLocked())) {
            throw new BaseResponseException(ResponseStatusCodeEnum.ACCOUNT_LOCKED);
        }

        String keyVerifyTokenForgotPassword = String.format(VERIFY_TOKEN_FORGOT_PASSWORD_KEY, email);
        String verificationToken = this.saveVerificationToken(keyVerifyTokenForgotPassword);
        this.sendMailForgotPassword(user, verificationToken);
        return new CommonResponse().setStatus(Boolean.TRUE).setMessage("Create forgot password successfully");
    }

    @Override
    public CommonResponse verifyForgotPassword(String email, String token, String newPassword) {
        String keyVerifyTokenForgotPassword = String.format(VERIFY_TOKEN_FORGOT_PASSWORD_KEY, email);
        this.validateVerifyToken(token, keyVerifyTokenForgotPassword);

        User user = userStore.getUserByEmail(email);
        if (ObjectUtils.isEmpty(user)) {
            throw new BaseResponseException(ResponseStatusCodeEnum.INCORRECT_EMAIL);
        }
        if (passwordEncoder.matches(newPassword, user.getPassword())) {
            throw new BaseResponseException(ResponseStatusCodeEnum.NEW_PASSWORD_MATCHES_OLD_PASSWORD);
        }
        userStore.updatePasswordByEmail(email, passwordEncoder.encode(newPassword));
        return new CommonResponse().setStatus(Boolean.TRUE).setMessage("Forgot password successfully");
    }

    private void sendMailSignUp(UserCreateRequest user, String verificationToken) {
        SendMailRequest sendMailRequest = new SendMailRequest();
        sendMailRequest.setTo(user.getEmail());
        sendMailRequest.setSubject(MailConstants.SIGNUP_SUBJECT);
        Map<String, Object> properties = new HashMap<>();
        properties.put("name", user.getLastName() + " " + user.getFirstName());
        properties.put("token", verificationToken);
        sendMailRequest.setProperties(properties);
        sendMailUtils.sendEmailWithHTML(sendMailRequest, MailConstants.SIGNUP_TEMPLATE);
    }

    private void sendMailForgotPassword(User user, String verificationToken) {
        SendMailRequest sendMailRequest = new SendMailRequest();
        sendMailRequest.setTo(user.getEmail());
        sendMailRequest.setSubject(MailConstants.FORGOT_PASSWORD_SUBJECT);
        Map<String, Object> properties = new HashMap<>();
        properties.put("name", user.getLastName() + " " + user.getFirstName());
        properties.put("token", verificationToken);
        sendMailRequest.setProperties(properties);
        sendMailUtils.sendEmailWithHTML(sendMailRequest, MailConstants.FORGOT_PASSWORD_TEMPLATE);
    }

    private void validateVerifyToken(String token, String keyVerifyToken) {
        String tokenVerify = externalCacheStore.getObject(keyVerifyToken, String.class);
        if (StringUtils.isEmpty(tokenVerify)) {
            throw new BaseResponseException(ResponseStatusCodeEnum.EXPIRED_TOKEN);
        }
        if (!token.equals(tokenVerify)) {
            throw new BaseResponseException(ResponseStatusCodeEnum.INVALID_TOKEN);
        }
        externalCacheStore.delete(keyVerifyToken);
    }

    private String saveVerificationToken(String key) {
        String token = DataUtils.generateOTP(6);
        externalCacheStore.putObject(key, token, 180);
        return token;
    }

}
