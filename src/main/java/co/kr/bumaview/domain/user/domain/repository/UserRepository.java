package co.kr.bumaview.domain.user.domain.repository;

import co.kr.bumaview.domain.user.domain.User;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.id = :userId")
    Optional<User> findByUserId(@Param("userId") String userId);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.id = :userId")
    boolean existsByUserId(@Param("userId") String userId);
}
