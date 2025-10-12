package co.kr.bumaview.domain.auth.domain.repository;

import co.kr.bumaview.domain.auth.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<RefreshToken, String> {

    int deleteByRefreshToken(String token);
}
