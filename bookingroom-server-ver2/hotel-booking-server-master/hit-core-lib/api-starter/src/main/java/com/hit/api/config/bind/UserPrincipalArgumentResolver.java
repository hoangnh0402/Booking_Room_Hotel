package com.hit.api.config.bind;

import com.hit.api.config.annotation.UserPrincipalRequest;
import com.hit.api.utils.IPAddressUtils;
import com.hit.api.utils.SecurityUtils;
import com.hit.common.core.exception.BaseResponseException;
import com.hit.common.core.exception.ResponseStatusCodeEnum;
import com.hit.common.core.domain.model.SimpleSecurityUser;
import com.hit.common.core.domain.model.UserPrincipal;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class UserPrincipalArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterAnnotation(UserPrincipalRequest.class) != null;
    }

    @Override
    public UserPrincipal resolveArgument(
            MethodParameter methodParameter,
            ModelAndViewContainer modelAndViewContainer,
            NativeWebRequest nativeWebRequest,
            WebDataBinderFactory webDataBinderFactory) {
        UserPrincipalRequest userPrincipalRequestAnnotation =
                methodParameter.getParameterAnnotation(UserPrincipalRequest.class);
        HttpServletRequest request = (HttpServletRequest) nativeWebRequest.getNativeRequest();

        UserPrincipal userPrincipal = new UserPrincipal();
        userPrincipal.setIp(IPAddressUtils.getClientIpAddress(request));
        userPrincipal.setMethod(request.getMethod());
        userPrincipal.setUri(request.getRequestURI());

        Authentication authentication = SecurityUtils.getCurrentAuthentication();
        if (authentication != null) {
            SimpleSecurityUser simpleSecurityUser = SecurityUtils.extractUserInfo(authentication);
            if (userPrincipalRequestAnnotation != null && userPrincipalRequestAnnotation.userRequired()
                    && simpleSecurityUser == null) {
                throw new BaseResponseException(ResponseStatusCodeEnum.UNAUTHORIZED_ERROR);
            }
            userPrincipal.setUser(simpleSecurityUser);
        }
        return userPrincipal;
    }
}
