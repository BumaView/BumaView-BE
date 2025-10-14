package co.kr.bumaview.domain.interview.presentation.dto.res;

import co.kr.bumaview.domain.interview.domain.AnswerRecord;

import java.util.List;

public record InterviewSummaryRes(
        Long interviewId,
        Summary summary
) {
    public record Summary(
            Integer totalQuestions,
            Integer totalTimeSpent,
            Double average,
            List<AnswerRecord> answers
    ) {}
}