package com.hit.hotel.socket.server.util;

import com.corundumstudio.socketio.HandshakeData;
import com.hit.common.core.constants.HeaderConstant;
import com.hit.common.core.domain.model.SimpleSecurityUser;
import com.hit.common.core.json.JsonMapper;
import com.hit.common.core.jwt.JwtProvider;
import com.hit.hotel.socket.server.config.authentication.Authentication;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;

import java.util.Locale;

@Log4j2
@UtilityClass
public class SocketIOUtils {

    public void setLocaleContext(HandshakeData handshakeData) {
        String localeHeader = handshakeData.getHttpHeaders().get(HttpHeaders.ACCEPT_LANGUAGE);
        Locale locale = Locale.of("vi");
        if (StringUtils.isNotEmpty(localeHeader)) {
            try {
                locale = Locale.forLanguageTag(localeHeader);
            } catch (Exception e) {
                log.warn("setLocaleContext localeHeader invalid!");
            }
        }
        LocaleContextHolder.setLocale(locale);
    }

    public Authentication getAuthentication(HandshakeData handshakeData, JwtProvider jwtProvider, String serverKey) {
        io.netty.handler.codec.http.HttpHeaders httpHeaders = handshakeData.getHttpHeaders();
        SimpleSecurityUser simpleSecurityUser = null;
        Boolean isSystem = Boolean.FALSE;
        if (httpHeaders.get(HeaderConstant.SERVER_TOKEN) != null) {
            log.debug("[Socket] getAuthentication server token");
            String serverToken = httpHeaders.get(HeaderConstant.SERVER_TOKEN);
            String userId = httpHeaders.get(HeaderConstant.USER_ID);
            if (serverToken.equalsIgnoreCase(serverKey)) {
                simpleSecurityUser = SimpleSecurityUser.builder().id(userId).build();
                isSystem = Boolean.TRUE;
            }
        } else if (StringUtils.isNotEmpty(httpHeaders.get(HeaderConstant.AUTHORIZATION))) {
            String bearerToken = httpHeaders.get(HeaderConstant.AUTHORIZATION).substring(7);
            simpleSecurityUser = jwtProvider.extractToken(bearerToken);
            log.debug("[Socket] getAuthentication bearer token");
        } else {
            log.debug("[Socket] getAuthentication no token");
        }
        Authentication authentication = new Authentication()
                .setUser(simpleSecurityUser)
                .setIsSystem(isSystem);
        log.debug("[Socket] getAuthentication authentication = {}", JsonMapper.encode(authentication));
        return authentication;
    }

}