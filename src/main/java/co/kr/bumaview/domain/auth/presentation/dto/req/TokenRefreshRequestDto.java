package co.kr.bumaview.domain.auth.presentation.dto.req;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TokenRefreshRequestDto {
    private String refreshToken;
}