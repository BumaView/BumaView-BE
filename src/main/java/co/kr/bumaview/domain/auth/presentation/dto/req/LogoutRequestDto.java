package co.kr.bumaview.domain.auth.presentation.dto.req;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LogoutRequestDto {

    @NotBlank
    private String refreshToken;
}
