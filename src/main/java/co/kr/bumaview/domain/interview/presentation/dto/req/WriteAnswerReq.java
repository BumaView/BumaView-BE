package co.kr.bumaview.domain.interview.presentation.dto.req;

import lombok.Getter;

@Getter
public class WriteAnswerReq {
    private Long questionId;
    private String answer;
    private int timeSpent;
}