package co.kr.bumaview.domain.auth.presentation.dto.res;

import co.kr.bumaview.domain.user.domain.UserInfo;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponseDto {

    private String userType;
    private String userId;
    private String name;
    private String accessToken;
    private String refreshToken;

    public static LoginResponseDto of(UserInfo userInfo, String accessToken, String refreshToken) {
        return LoginResponseDto.builder()
                .userType(userInfo.getUserType().name())
                .userId(userInfo.getUserId())
                .name(userInfo.getName())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
