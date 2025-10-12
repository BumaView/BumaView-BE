package co.kr.bumaview.global.infra;

import co.kr.bumaview.domain.user.domain.UserInfo;
import co.kr.bumaview.domain.user.domain.UserReader;
import co.kr.bumaview.domain.user.domain.type.Authority;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserReaderImpl implements UserReader {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<UserInfo> findByEmail(String email) {
        try {
            String query = "SELECT id, name, email, user_type FROM users WHERE email = ?";
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            query,
                            (rs, rowNum) -> UserInfo.builder()
                                    .userId(rs.getLong("id"))
                                    .name(rs.getString("name"))
                                    .userType(Authority.valueOf(rs.getString("user_type").toUpperCase()))
                                    .build(),
                            email
                    )
            );
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public UserInfo read(Class<?> clazz, Long id) {
        try {
            String query = "SELECT id, name, email, user_type FROM users WHERE id = ?";
            return jdbcTemplate.queryForObject(
                    query,
                    (rs, rowNum) -> UserInfo.builder()
                            .userId(rs.getLong("id"))
                            .name(rs.getString("name"))
                            .userType(Authority.valueOf(rs.getString("user_type").toUpperCase()))
                            .build(),
                    id
            );
        } catch (EmptyResultDataAccessException e) {
            throw new RuntimeException("User not found with id: " + id);
        }
    }
}
