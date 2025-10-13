package co.kr.bumaview.domain.question.presentation.dto.req;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import java.util.List;

@Getter
public class QuestionRequest {

    @NotEmpty(message = "질문 목록은 비어있을 수 없습니다")
    @Valid
    private List<Question> questions;

    @Getter
    public static class Question {
        @NotBlank(message = "질문 내용은 비어있을 수 없습니다")
        private String q;
    }
}