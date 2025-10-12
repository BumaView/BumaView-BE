package co.kr.bumaview.domain.auth.presentation;

import co.kr.bumaview.domain.auth.domain.service.OAuthLoginService;
import co.kr.bumaview.domain.auth.presentation.dto.req.GoogleLoginRequestDto;
import co.kr.bumaview.domain.auth.presentation.dto.req.LogoutRequestDto;
import co.kr.bumaview.domain.auth.presentation.dto.req.SignUpRequest;
import co.kr.bumaview.domain.auth.presentation.dto.req.TokenRefreshRequestDto;
import co.kr.bumaview.domain.auth.presentation.dto.res.LoginResponseDto;
import co.kr.bumaview.domain.auth.presentation.dto.res.LogoutResponseDto;
import co.kr.bumaview.domain.auth.presentation.dto.res.RefreshResponse;
import co.kr.bumaview.domain.auth.presentation.dto.res.SignUpResponse;
import co.kr.bumaview.domain.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthService authService;
    private final OAuthLoginService oAuthLoginService;

    @PostMapping("/sign-up")
    public ResponseEntity<SignUpResponse> signUp(
            @RequestBody SignUpRequest signUpRequest
    ){
        SignUpResponse signUpResponse = authService.signUp(signUpRequest);
        return ResponseEntity.ok(signUpResponse);
    }

    @PostMapping("/login/google")
    public ResponseEntity<LoginResponseDto> loginWithGoogle(
            @RequestBody GoogleLoginRequestDto request
    ) {
        log.info("=== Google 로그인 시도 시작 ===");
        log.info("Authorization Code: {}", request.getAuthorizationCode());

        try {
            LoginResponseDto response = oAuthLoginService.loginWithGoogle(request.getAuthorizationCode());
            log.info("=== Google 로그인 성공 ===");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("=== Google 로그인 실패: {} ===", e.getMessage(), e);
            throw e;
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshResponse> refreshAccessToken(
            @RequestBody TokenRefreshRequestDto request
    ) {
        log.info("=== 토큰 리프레시 시도 ===");
        return ResponseEntity.ok(authService.refreshToken(request));
    }


    @PostMapping("/logout")
    public ResponseEntity<LogoutResponseDto> logout(
            @Valid @RequestBody LogoutRequestDto logoutRequestDto
    ) {
        authService.logout(logoutRequestDto);
        return ResponseEntity.ok(new LogoutResponseDto("로그아웃 되었습니다."));
    }
}
