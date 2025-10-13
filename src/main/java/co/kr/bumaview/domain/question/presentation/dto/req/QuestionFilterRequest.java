package co.kr.bumaview.domain.question.presentation.dto.req;

import lombok.Getter;

@Getter
public class QuestionFilterRequest {
    private String company;
    private Integer year;
    private String category;
}