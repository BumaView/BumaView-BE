package co.kr.bumaview.domain.auth.presentation.dto.req;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "password")
public class SignUpRequest {
    @NotBlank(message = "아이디는 필수입니다")
    private String id;
    @NotBlank(message = "이메일은 필수입니다")
    @Email(message = "유효한 이메일 형식이어야 합니다")
    private String email;
    @NotBlank(message = "닉네임은 필수입니다")
    private String nickname;
    @NotBlank(message = "비밀번호는 필수입니다")
    @Size(min = 4, max = 10, message = "비밀번호는 4자 이상 10자 이하여야 합니다")
    private String password;
    @NotBlank(message = "사용자 타입은 필수입니다")
    @Pattern(regexp = "^(USER|ADMIN)$", message = "사용자 타입은 USER 또는 ADMIN이어야 합니다")
    private String userType;
}
