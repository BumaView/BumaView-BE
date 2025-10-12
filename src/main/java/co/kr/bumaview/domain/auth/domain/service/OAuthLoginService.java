package co.kr.bumaview.domain.auth.domain.service;

import co.kr.bumaview.domain.auth.presentation.dto.res.LoginResponseDto;
import co.kr.bumaview.domain.user.domain.UserInfo;
import co.kr.bumaview.domain.user.domain.UserReader;
import co.kr.bumaview.global.infra.GoogleOAuthClient;
import co.kr.bumaview.global.infra.GoogleUserInfoDto;
import co.kr.bumaview.global.security.jwt.JwtProvider;
import com.google.api.client.auth.oauth2.TokenResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuthLoginService {

    private final GoogleOAuthClient googleOAuthClient;
    private final UserReader userReader;
    private final JwtProvider jwtProvider;

    public LoginResponseDto loginWithGoogle(String authorizationCode) {
        try {
            log.info("[OAuth] Starting Google login process with code: {}",
                    authorizationCode != null ? "present" : "null");

            // 1. Google에서 토큰 가져오기
            TokenResponse token = googleOAuthClient.exchangeCodeForToken(authorizationCode);
            if (token == null || token.getAccessToken() == null || token.getAccessToken().isBlank()) {
                log.error("[OAuth] Failed to get token from Google. Token: {}", token);
                throw new RuntimeException("Failed to get token from Google");
            }
            log.info("[OAuth] Successfully got token from Google");

            // 2. Google에서 사용자 정보 가져오기
            GoogleUserInfoDto userInfoDto = googleOAuthClient.getUserInfo(token.getAccessToken());
            if (userInfoDto == null || userInfoDto.getEmail() == null || userInfoDto.getEmail().isBlank()) {
                log.error("[OAuth] Failed to get user info from Google. UserInfo: {}", userInfoDto);
                throw new RuntimeException("Failed to get user info from Google");
            }

            String email = userInfoDto.getEmail().trim().toLowerCase();
            log.info("[OAuth] Got user info - email: {}, verified: {}, domain: {}",
                    email, userInfoDto.getEmail_verified(), userInfoDto.getHd());

            // 3. DB에서 사용자 찾기 (없으면 에러)
            UserInfo userInfo = userReader.findByEmail(email)
                    .orElseThrow(() -> {
                        log.warn("[OAuth] User not found in database: {}", email);
                        return new RuntimeException("Unregistered user");
                    });

            log.info("[OAuth] User found - ID: {}, Type: {}, Name: {}",
                    userInfo.getUserId(), userInfo.getUserType(), userInfo.getName());

            // 4. JWT 토큰 생성
            String accessToken = jwtProvider.createAccessToken(userInfo.getUserId(), userInfo.getUserType().name());
            String refreshToken = jwtProvider.createRefreshToken(userInfo.getUserId(), userInfo.getUserType());

            log.info("[OAuth] Generated JWT tokens for user: {}", userInfo.getUserId());

            // 6. 로그인 응답 생성
            LoginResponseDto response = LoginResponseDto.of(userInfo, accessToken, refreshToken);
            log.info("[OAuth] Login successful for user: {}", email);

            return response;

        } catch (RuntimeException e) {
            log.error("[OAuth] Custom exception during login: {} - {}", e.getMessage(), e.getClass().getSimpleName());
            throw e;
        } catch (Exception e) {
            log.error("[OAuth] Unexpected error during login", e);
            throw new RuntimeException("Login failed due to unexpected error");
        }
    }
}
