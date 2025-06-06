package kuit.springbasic.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {
    private final long JWT_ACCESS_EXPIRED_IN;
    private final long JWT_REFRESH_EXPIRED_IN;

    private final SecretKey accessSecretKey;
    private final SecretKey refreshSecretKey;

    public JwtTokenProvider(
            @Value("${secret.jwt.access.key}") String accessSecretKey,
            @Value("${secret.jwt.refresh.key}") String refreshSecretKey,
            @Value("${secret.jwt.access.expired-in}") long jwtAccessExpiredIn,
            @Value("${secret.jwt.refresh.expired-in}") long jwtRefreshExpiredIn
    ) {
        this.accessSecretKey = getSecretKey(accessSecretKey);
        this.refreshSecretKey = getSecretKey(refreshSecretKey);

        this.JWT_ACCESS_EXPIRED_IN = jwtAccessExpiredIn;
        this.JWT_REFRESH_EXPIRED_IN = jwtRefreshExpiredIn;
    }

    private static SecretKey getSecretKey(String secretKey) {
        return Keys.hmacShaKeyFor(Base64.getDecoder().decode(
                secretKey.getBytes(StandardCharsets.UTF_8)));
    }

    public JwtInfo createToken(String principal, String userId){
        Date now = new Date();
        Date accessValidity = new Date(now.getTime() + JWT_ACCESS_EXPIRED_IN);
        Date refreshValidity = new Date(now.getTime() + JWT_REFRESH_EXPIRED_IN);

        String accessToken = Jwts.builder()
                .subject(principal)
                .issuedAt(now)
                .expiration(accessValidity)
                .claim("userId", userId)
                .signWith(accessSecretKey)
                .compact();

        String refreshToken = Jwts.builder()
                .subject(principal)
                .issuedAt(now)
                .expiration(refreshValidity)
                .claim("userId", userId)
                .signWith(refreshSecretKey)
                .compact();

        return new JwtInfo(accessToken, refreshToken);
    }

    public String validateToken(String token){
        Claims payload = Jwts.parser()
                .verifyWith(accessSecretKey).build()
                .parseSignedClaims(token).getPayload();

        return getUserId(payload);
    }

    private String getUserId(Claims payload){
        return payload.get("userId", String.class);
    }
}
