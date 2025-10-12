package co.kr.bumaview.domain.user.domain;

import co.kr.bumaview.domain.user.domain.type.Authority;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public class CustomUserDetails implements UserDetails {

    private final String userId;
    private final String userType;

    public CustomUserDetails(String userId, String userType) {
        this.userId = userId;
        this.userType = userType;
    }

    @Override
    public String getUsername() {
        return userId; // 굳이 필요 없으면 그냥 ID 리턴
    }

    @Override
    public String getPassword() {
        return null; // JWT 인증 시 password 사용 안 함
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (Authority.ADMIN.name().equals(userType)) {
            Stream<GrantedAuthority> userTypeRoles = Arrays.stream(Authority.values())
                    .map(type -> new SimpleGrantedAuthority("ROLE_" + type.name()));
            Stream<GrantedAuthority> roleEnumRoles = Arrays.stream(Authority.values())
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()));
            return Stream.concat(userTypeRoles, roleEnumRoles).collect(Collectors.toList());
        }
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + userType));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
