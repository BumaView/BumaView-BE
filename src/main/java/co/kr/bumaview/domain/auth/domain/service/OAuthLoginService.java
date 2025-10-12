package co.kr.bumaview.domain.auth.domain.service;

import co.kr.bumaview.domain.auth.presentation.dto.res.LoginResponseDto;
import co.kr.bumaview.domain.user.domain.User;
import co.kr.bumaview.domain.user.domain.UserInfo;
import co.kr.bumaview.domain.user.domain.UserReader;
import co.kr.bumaview.domain.user.domain.repository.UserRepository;
import co.kr.bumaview.domain.user.domain.type.Authority;
import co.kr.bumaview.global.infra.GoogleOAuthClient;
import co.kr.bumaview.global.infra.GoogleUserInfoDto;
import co.kr.bumaview.global.security.jwt.JwtProvider;
import com.google.api.client.auth.oauth2.TokenResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuthLoginService {

    private final GoogleOAuthClient googleOAuthClient;
    private final UserReader userReader;
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    @Transactional
    public LoginResponseDto loginWithGoogle(String authorizationCode) {
        try {
            log.info("[OAuth] Starting Google login process with code: {}",
                    authorizationCode != null ? "present" : "null");

            TokenResponse token = googleOAuthClient.exchangeCodeForToken(authorizationCode);
            if (token == null || token.getAccessToken() == null || token.getAccessToken().isBlank()) {
                log.error("[OAuth] Failed to get token from Google. Token: {}", token);
                throw new RuntimeException("Failed to get token from Google");
            }
            log.info("[OAuth] Successfully got token from Google");

            GoogleUserInfoDto userInfoDto = googleOAuthClient.getUserInfo(token.getAccessToken());
            if (userInfoDto == null || userInfoDto.getEmail() == null || userInfoDto.getEmail().isBlank()) {
                log.error("[OAuth] Failed to get user info from Google. UserInfo: {}", userInfoDto);
                throw new RuntimeException("Failed to get user info from Google");
            }

            String email = userInfoDto.getEmail().trim().toLowerCase();
            log.info("[OAuth] Got user info - email: {}, verified: {}, domain: {}",
                    email, userInfoDto.getEmail_verified(), userInfoDto.getHd());

            UserInfo userInfo = userReader.findByEmail(email)
                    .orElseGet(() -> {
                        log.warn("[OAuth] User not found in database: {}. Creating new user.", email);
                        User newUser = User.builder()
                                .id(email)
                                .email(email)
                                .username(email)
                                .role(Authority.USER)
                                .password(null)
                                .build();
                        userRepository.save(newUser);
                        return UserInfo.builder()
                                .userId(newUser.getId())
                                .name(newUser.getUsername())
                                .userType(newUser.getRole())
                                .build();
                    });

            log.info("[OAuth] User found or created - ID: {}, Type: {}, Name: {}",
                    userInfo.getUserId(), userInfo.getUserType(), userInfo.getName());

            String accessToken = jwtProvider.createAccessToken(userInfo.getUserId(), userInfo.getUserType().name());
            String refreshToken = jwtProvider.createRefreshToken(userInfo.getUserId(), userInfo.getUserType());

            log.info("[OAuth] Generated JWT tokens for user: {}", userInfo.getUserId());

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
