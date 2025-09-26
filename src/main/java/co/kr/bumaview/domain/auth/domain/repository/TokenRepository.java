package co.kr.bumaview.domain.auth.domain.repository;

import co.kr.bumaview.domain.auth.domain.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Long> {
}
