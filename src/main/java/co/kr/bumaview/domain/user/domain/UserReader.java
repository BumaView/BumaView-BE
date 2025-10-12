package co.kr.bumaview.domain.user.domain;

import java.util.Optional;

public interface UserReader {
    Optional<UserInfo> findByEmail(String email);

    UserInfo read(Class<?> clazz, String id);
}