package co.kr.bumaview.domain.auth.presentation.dto.req;

import lombok.Getter;

@Getter
public class GoogleLoginRequestDto {
    private String authorizationCode;
}
