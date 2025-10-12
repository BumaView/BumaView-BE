package co.kr.bumaview.domain.question.presentation.dto.req;

import lombok.Getter;
import java.util.List;

@Getter
public class QuestionRequest {

    private List<Question> questions;

    @Getter
    public static class Question {
        private String q;
    }
}