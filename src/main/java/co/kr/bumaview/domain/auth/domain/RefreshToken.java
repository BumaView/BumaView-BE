package co.kr.bumaview.domain.auth.domain;

import co.kr.bumaview.domain.user.domain.type.Authority;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="refresh_token")
@Getter
@NoArgsConstructor
public class RefreshToken {
    @Id
    private String userId;
    @Column(name = "refresh_token", nullable = false, unique = true)
    private String refreshToken;
    @Enumerated(EnumType.STRING)
    @Column(name = "user_type", nullable = false)
    private Authority userType;

    public void updateToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public RefreshToken(String userId, String refreshToken, Authority userType) {
        this.userId = userId;
        this.refreshToken = refreshToken;
        this.userType = userType;
    }
}