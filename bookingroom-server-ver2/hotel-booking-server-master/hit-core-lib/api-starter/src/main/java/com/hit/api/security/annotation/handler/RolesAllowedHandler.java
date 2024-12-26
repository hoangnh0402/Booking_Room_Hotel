package com.hit.api.security.annotation.handler;

import com.hit.api.security.annotation.RolesAllowed;
import com.hit.api.utils.SecurityUtils;
import com.hit.common.core.exception.BaseResponseException;
import com.hit.common.core.exception.ResponseStatusCodeEnum;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.CollectionUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;

import java.util.Arrays;
import java.util.List;

@Log4j2
@Aspect
@Configuration
public class RolesAllowedHandler {

    @Before("@annotation(rolesAllowed)")
    public void before(JoinPoint joinPoint, RolesAllowed rolesAllowed) {
        log.info("Before called {}", joinPoint.toString());
        boolean isValid = false;
        List<String> roles = Arrays.asList(rolesAllowed.role());
        Authentication authentication = SecurityUtils.getCurrentAuthentication();
        List<String> authorities = SecurityUtils.getAuthorities(authentication);
        if (CollectionUtils.isNotEmpty(authorities)) {
            for (String authority : authorities) {
                if (roles.contains(authority)) {
                    isValid = true;
                    break;
                }
            }
        }
        if (!isValid) {
            throw new BaseResponseException(ResponseStatusCodeEnum.FORBIDDEN_ERROR);
        }
    }
}
