package co.kr.bumaview.domain.auth.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="refresh_token")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RefreshToken {
    @Id
    private String id;
    @Column(name = "refresh_token", nullable = false, unique = true)
    private String refreshToken;
    @Column(name = "user_type", nullable = false)
    private String userType;

    public void updateToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}