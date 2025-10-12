package co.kr.bumaview.domain.auth.domain.repository;

import co.kr.bumaview.domain.auth.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> deleteByRefreshToken(String token);
}
