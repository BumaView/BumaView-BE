package co.kr.bumaview.domain.question.presentation.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class QuestionSheetResponse {
    private String message;
    private Object data;
}