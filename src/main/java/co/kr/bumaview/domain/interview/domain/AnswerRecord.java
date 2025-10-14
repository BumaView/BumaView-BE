package co.kr.bumaview.domain.interview.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AnswerRecord {
    private Long questionId;
    private String answer;
    private int timeSpent;
    private LocalDateTime recordedAt;
}