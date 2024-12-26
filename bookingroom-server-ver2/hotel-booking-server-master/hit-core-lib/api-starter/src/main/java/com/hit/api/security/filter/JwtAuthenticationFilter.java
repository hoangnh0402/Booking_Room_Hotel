package com.hit.api.security.filter;

import com.hit.api.factory.ResponseFactory;
import com.hit.api.security.authorization.UserAuthority;
import com.hit.common.core.constants.HeaderConstant;
import com.hit.common.core.exception.BaseResponseException;
import com.hit.common.core.exception.ResponseStatusCodeEnum;
import com.hit.common.core.jwt.JwtProvider;
import com.hit.common.core.domain.model.SimpleSecurityUser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static org.apache.commons.lang3.StringUtils.isEmpty;

@Log4j2
@Order(1)
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Value("${app.security.server-key}")
    private String serverKey;

    private final JwtProvider jwtProvider;

    private static final List<String> API_PUBLIC = List.of(
            "/swagger-ui",
            "/auth/login"
    );

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        try {
            if (API_PUBLIC.stream().anyMatch(request.getRequestURI()::contains)) {
                log.debug("Api public: {}", request.getRequestURI());
                filterChain.doFilter(request, response);
                return;
            }

            String userToken = request.getHeader(HeaderConstant.AUTHORIZATION);
            String serverToken = request.getHeader(HeaderConstant.SERVER_TOKEN);
            UsernamePasswordAuthenticationToken authentication;
            if (StringUtils.isNotEmpty(serverToken)) {
                String userId = request.getHeader(HeaderConstant.USER_ID);
                if (!serverToken.equalsIgnoreCase(serverKey) && !isEmpty(userId)) {
                    throw new BaseResponseException(ResponseStatusCodeEnum.UNAUTHORIZED_ERROR);
                }
                SimpleSecurityUser user = SimpleSecurityUser.builder()
                        .id(userId)
                        .build();
                authentication = new UsernamePasswordAuthenticationToken(userId, user, emptyList());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else if (StringUtils.isNotEmpty(userToken) && userToken.startsWith("Bearer")) {
                String token = userToken.replaceFirst("Bearer", "").trim();
                SimpleSecurityUser user = jwtProvider.extractToken(token);
                if (user == null) {
                    throw new BaseResponseException(ResponseStatusCodeEnum.UNAUTHORIZED_ERROR);
                }
                authentication = new UsernamePasswordAuthenticationToken(user.getId(), user, this.getAuthorities(user));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            filterChain.doFilter(request, response);
        } catch (BaseResponseException e) {
            log.error("JwtAuthenticationFilter ERROR", e);
            ResponseFactory.httpServletResponseToClient(response, e.getResponseStatusCode());
        } catch (Exception e) {
            log.error("JwtAuthenticationFilter ERROR", e);
            ResponseFactory.httpServletResponseToClient(response, ResponseStatusCodeEnum.UNAUTHORIZED_ERROR);
        }
    }

    public List<UserAuthority> getAuthorities(SimpleSecurityUser user) {
        return Optional.ofNullable(user.getAuthorities())
                .orElse(Collections.emptyList())
                .stream()
                .map(UserAuthority::new)
                .toList();
    }

}


