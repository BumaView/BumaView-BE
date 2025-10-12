package co.kr.bumaview.domain.auth.domain.repository;

import co.kr.bumaview.domain.auth.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<RefreshToken, Long> {

    int deleteByRefreshToken(String token);

    @Query("SELECT r FROM RefreshToken r WHERE r.id = :id")
    Optional<RefreshToken> findByUserId(@Param("id") String id);
}
