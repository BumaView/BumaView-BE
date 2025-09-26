package co.kr.bumaview.global.security.jwt;

import co.kr.bumaview.domain.auth.domain.repository.TokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtProvider {

    private final TokenRepository tokenRepository;

    @Value("${jwt.secret-key}")
    private String secretKey;

    private static final long ACCESS_TOKEN_VALIDITY = 1000 * 60 * 60 * 24;
    private static final long REFRESH_TOKEN_VALIDITY = 1000 * 60 * 60 * 24 * 7;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public Claims parseToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            log.error("토큰이 만료되었습니다. token: {}", token, e);
            throw new RuntimeException("토큰이 만료되었습니다.");
        } catch (JwtException e) {
            log.error("토큰이 만료되었습니다. token: {}", token, e);
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }
    }
}