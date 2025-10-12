package co.kr.bumaview.domain.auth.service;

import co.kr.bumaview.domain.auth.domain.repository.TokenRepository;
import co.kr.bumaview.domain.auth.presentation.dto.req.LogoutRequestDto;
import co.kr.bumaview.domain.auth.presentation.dto.req.SignUpRequest;
import co.kr.bumaview.domain.auth.presentation.dto.req.TokenRefreshRequestDto;
import co.kr.bumaview.domain.auth.presentation.dto.res.RefreshResponse;
import co.kr.bumaview.domain.auth.presentation.dto.res.SignUpResponse;
import co.kr.bumaview.domain.user.domain.User;
import co.kr.bumaview.domain.user.domain.repository.UserRepository;
import co.kr.bumaview.domain.user.domain.type.Authority;
import co.kr.bumaview.global.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    private final JwtProvider jwtProvider;


    public SignUpResponse signUp(SignUpRequest req) {
        try {
            User user = User.builder()
                    .id(req.getId())
                    .email(req.getEmail())
                    .password(passwordEncoder.encode(req.getPassword()))
                    .username(req.getNickname())
                    .role(Authority.valueOf(req.getUserType()))
                    .build();

            userRepository.save(user);

            return new SignUpResponse(200L, "회원가입 성공");
        } catch (Exception e) {
            log.error("회원가입 실패: {}", e.getMessage(), e);
            return new SignUpResponse(400L, "회원가입 실패: " + e.getMessage());
        }
    }

    @Transactional
    public void logout(LogoutRequestDto logoutRequestDto) {
        String refreshToken = logoutRequestDto.getRefreshToken();

        boolean deleted = tokenRepository.deleteByRefreshToken(refreshToken) > 0;
        if (!deleted) {
            throw new IllegalArgumentException("유효한 리프레시 토큰이 없습니다.");
        }
    }

    private void validateToken(String token) {
        if (token == null || token.isBlank()) {
            throw new IllegalArgumentException("refreshToken 값이 비어 있을 수 없습니다.");
        }
    }

    public RefreshResponse refreshToken(TokenRefreshRequestDto requestDto) {
        String refreshToken = requestDto.getRefreshToken();

        if (!jwtProvider.isValidRefreshToken(refreshToken)) {
            throw new IllegalArgumentException("리프레시 토큰이 유효하지 않습니다.");
        }

        String newAccessToken = jwtProvider.createAccessTokenByRefreshToken(requestDto);

        return new RefreshResponse(newAccessToken);
    }
}
