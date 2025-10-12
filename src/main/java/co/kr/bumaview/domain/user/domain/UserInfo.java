package co.kr.bumaview.domain.user.domain;

import co.kr.bumaview.domain.user.domain.type.Authority;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserInfo {
    private Long userId;
    private String name;
    private Authority userType;
}
