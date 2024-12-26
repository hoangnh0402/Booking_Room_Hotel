package com.hit.api.utils;

import com.hit.common.core.domain.model.SimpleSecurityUser;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.math.NumberUtils.isDigits;

public class SecurityUtils {
    private SecurityUtils() {
    }

    public static Authentication getCurrentAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static List<String> getAuthorities(Authentication authentication) {
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken)
            return new ArrayList<>();
        return authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
    }

    public static String getUserId(Authentication authentication) {
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken ||
                authentication.getAuthorities() == null ||
                isDigits(authentication.getPrincipal().toString())) return null;
        return authentication.getPrincipal().toString();
    }

    public static SimpleSecurityUser extractUserInfo(Authentication authentication) {
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken ||
                authentication.getAuthorities() == null ||
                isDigits(authentication.getPrincipal().toString())) return null;
        return (SimpleSecurityUser) authentication.getCredentials();
    }

}
