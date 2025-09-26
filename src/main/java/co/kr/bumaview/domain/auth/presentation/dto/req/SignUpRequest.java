package co.kr.bumaview.domain.auth.presentation.dto.req;

import co.kr.bumaview.domain.user.domain.type.Authority;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignUpRequest {
    private String id;
    private String nickname;
    private String password;
    private Authority userType;
}
