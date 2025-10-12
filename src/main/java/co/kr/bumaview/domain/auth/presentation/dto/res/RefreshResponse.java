package co.kr.bumaview.domain.auth.presentation.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RefreshResponse {
    private final String access_token;

}