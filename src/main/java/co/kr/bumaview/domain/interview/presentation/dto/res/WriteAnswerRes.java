package co.kr.bumaview.domain.interview.presentation.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class WriteAnswerRes {
    private Long interviewId;
    private Long questionId;
    private String answer;
    private int timeSpent;
    private LocalDateTime recordedAt;
}