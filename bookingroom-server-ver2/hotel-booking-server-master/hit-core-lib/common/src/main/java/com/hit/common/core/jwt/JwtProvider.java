package com.hit.common.core.jwt;

import com.hit.common.core.json.JsonMapper;
import com.hit.common.core.domain.model.SimpleSecurityUser;
import com.hit.common.utils.StringUtils;
import io.jsonwebtoken.*;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@Component
public class JwtProvider {

    public static final String CLAIM_TYPE = "type";
    private static final String TYPE_ACCESS = "access";
    private static final String TYPE_REFRESH = "refresh";
    private static final String USER = "user";

    @Value("${app.security.jwt.secret-key}")
    private String secretKey;

    @Value("${app.security.jwt.access-expire}")
    private Integer expirationTimeAccessToken;

    @Value("${app.security.jwt.refresh-expire}")
    private Integer expirationTimeRefreshToken;

    public String generateToken(SimpleSecurityUser simpleSecurityUser, Boolean isRefreshToken) {
        Map<String, Object> claim = new HashMap<>();
        claim.put(CLAIM_TYPE, BooleanUtils.isTrue(isRefreshToken) ? TYPE_REFRESH : TYPE_ACCESS);
        if (BooleanUtils.isTrue(isRefreshToken)) {
            return Jwts.builder()
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + (expirationTimeRefreshToken * 60 * 1000L)))
                    .signWith(SignatureAlgorithm.HS256, secretKey)
                    .compact();
        }
        claim.put(USER, Base64.encodeBase64String(JsonMapper.encodeAsByte(simpleSecurityUser)));
        return Jwts.builder()
                .setClaims(claim)
                .setSubject(simpleSecurityUser.getId())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (expirationTimeAccessToken * 60 * 1000L)))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public SimpleSecurityUser extractToken(String token) {
        if (token == null || StringUtils.isBlank(token)) return null;
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();
            String userPrincipal = (String) body.get(USER);
            return JsonMapper.decodeValue(Base64.decodeBase64(userPrincipal), SimpleSecurityUser.class);
        } catch (Exception e) {
            log.error("extractToken ERROR: {}", e.getMessage(), e);
            return null;
        }
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty");
        } catch (Exception ex) {
            log.error("JWT parse claims error", ex);
        }
        return false;
    }
}
