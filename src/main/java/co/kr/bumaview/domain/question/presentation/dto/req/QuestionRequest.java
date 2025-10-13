package co.kr.bumaview.domain.question.presentation.dto.req;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import java.util.List;

@Getter
public class QuestionRequest {

    @NotBlank(message = "질문은 비어있을 수 없습니다")
    private List<Question> questions;

    @Getter
    public static class Question {
        private String q;
    }
}