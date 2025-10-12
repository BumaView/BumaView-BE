package co.kr.bumaview.domain.auth.presentation.dto.req;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignUpRequest {
    private String id;
    private String email;
    private String nickname;
    private String password;
    private String userType;
}
