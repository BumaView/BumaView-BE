package co.kr.bumaview.domain.user.domain.repository;

import co.kr.bumaview.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
