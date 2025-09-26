package co.kr.bumaview.domain.auth.service;

import co.kr.bumaview.domain.auth.presentation.dto.req.SignUpRequest;
import co.kr.bumaview.domain.auth.presentation.dto.res.SignUpResponse;
import co.kr.bumaview.domain.user.domain.User;
import co.kr.bumaview.domain.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SignUpResponse signUp(SignUpRequest req) {
        try {
            User user = User.builder()
                    .id(req.getId())
                    .email(req.getId() + "@")
                    .password(passwordEncoder.encode(req.getPassword()))
                    .username(req.getNickname())
                    .role(req.getUserType())
                    .build();

            userRepository.save(user);

            return new SignUpResponse(200L, "회원가입 성공");
        } catch (Exception e) {
            log.error("회원가입 실패: {}", e.getMessage(), e);
            return new SignUpResponse(400L, "회원가입 실패: " + e.getMessage());
        }
    }
}
