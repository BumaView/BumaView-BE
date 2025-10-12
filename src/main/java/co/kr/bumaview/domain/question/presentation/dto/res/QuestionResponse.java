package co.kr.bumaview.domain.question.presentation.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class QuestionResponse {
    private String message;
    private List<RegisteredQuestion> registeredQuestions;

    @Getter
    @AllArgsConstructor
    public static class RegisteredQuestion {
        private Long id;
        private String q;
    }
}