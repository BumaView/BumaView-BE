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
import co.kr.bumaview.domain.user.domain.User;
import co.kr.bumaview.domain.user.domain.repository.UserRepository;
import co.kr.bumaview.global.security.jwt.JwtProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthService authService;
    private final OAuthLoginService oAuthLoginService;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;


    @PostMapping("/sign-up")
    public ResponseEntity<SignUpResponse> signUp(
            @RequestBody SignUpRequest signUpRequest
    ){
        if (userRepository.existsByUserId(signUpRequest.getId())) {
            return ResponseEntity.badRequest().body(new SignUpResponse(403L, "이미 존재하는 사용자입니다."));
        }
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
        log.info(">>> [AuthController] logout 호출됨.");
        authService.logout(logoutRequestDto);
        return ResponseEntity.ok(new LogoutResponseDto("로그아웃 되었습니다."));
    }

    //자체 로그인~~~
    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> request) {
        String userId = request.get("userId");
        String password = request.get("password");

        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        String accessToken = jwtProvider.generateAccessToken(userId, user.getRole().name());
        String refreshToken = jwtProvider.createRefreshToken(userId, user.getRole().name());

        return Map.of(
                "accessToken", accessToken,
                "refreshToken", refreshToken
        );
    }
}
