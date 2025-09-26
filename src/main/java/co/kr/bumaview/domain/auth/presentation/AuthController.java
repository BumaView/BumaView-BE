package co.kr.bumaview.domain.auth.presentation;

import co.kr.bumaview.domain.auth.presentation.dto.req.SignUpRequest;
import co.kr.bumaview.domain.auth.presentation.dto.res.SignUpResponse;
import co.kr.bumaview.domain.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/sign-up")
    public ResponseEntity<SignUpResponse> signUp(
            @RequestBody SignUpRequest signUpRequest
    ){
        SignUpResponse signUpResponse = authService.signUp(signUpRequest);
        return ResponseEntity.ok(signUpResponse);
    }
}
