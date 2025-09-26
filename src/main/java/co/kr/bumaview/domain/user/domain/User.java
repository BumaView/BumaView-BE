package co.kr.bumaview.domain.user.domain;

import co.kr.bumaview.domain.user.domain.type.Authority;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(name="users")
public class User {

    @Id
    private String id;

    @Column(unique = true)
    private String username;

    @Column(nullable = true)
    private String email;

    private Authority role;

    @Column(columnDefinition = "json")
    private String password;
}
