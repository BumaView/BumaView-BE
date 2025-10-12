package co.kr.bumaview.global.security.jwt;

import co.kr.bumaview.domain.auth.domain.RefreshToken;
import co.kr.bumaview.domain.auth.domain.repository.TokenRepository;
import co.kr.bumaview.domain.auth.presentation.dto.req.TokenRefreshRequestDto;
import co.kr.bumaview.domain.user.domain.CustomUserDetails;
import co.kr.bumaview.domain.user.domain.type.Authority;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    public String createAccessToken(String userId, String userType) {
        if("admin".equals(userType)) {
            return Jwts.builder()
                    .setSubject("AccessToken")
                    .claim("userId", userId)
                    .claim("userType", userType)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY))
                    .signWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
                    .compact();
        } else {
            return Jwts.builder()
                    .setSubject("AccessToken")
                    .claim("userId", userId)
                    .claim("userType", userType)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY))
                    .signWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
                    .compact();
        }
    }
    public String createRefreshToken(String userId, Authority userType) {

        String refreshToken = Jwts.builder()
                .setSubject("RefreshToken")
                .claim("userId", userId)
                .claim("userType", userType)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_VALIDITY))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
                .compact();

        tokenRepository.findById(userId)
                .ifPresentOrElse(
                        token -> token.updateToken(refreshToken), // update
                        () -> tokenRepository.save(new RefreshToken(userId, refreshToken, userType)) // insert
                );
        return refreshToken;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.JwtException | IllegalArgumentException e) {
            log.info("[JwtProvider] 토큰 검증 실패: {}", e.getMessage());
            return false;
        }
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseClaimsJws(token)
                .getBody();

        String userId = claims.get("userId", String.class);
        String userType = claims.get("userType").toString();

        List<GrantedAuthority> authorities = new ArrayList<>();
        if ("admin".equalsIgnoreCase(userType)) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else if ("user".equalsIgnoreCase(userType)) {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }

        CustomUserDetails userDetails = new CustomUserDetails(userId, userType);

        return new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
    }


    public boolean isValidRefreshToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String createAccessTokenByRefreshToken(TokenRefreshRequestDto refreshToken) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseClaimsJws(refreshToken.getRefreshToken())
                .getBody();

        String userId = claims.get("userId", String.class);
        String userType = claims.get("userType", String.class);

        return createAccessToken(userId, userType);
    }

}