package co.kr.bumaview.domain.interview.presentation.dto.res;

import co.kr.bumaview.domain.question.presentation.dto.req.QuestionDto;

import java.time.LocalDateTime;
import java.util.List;

public record CreateInterviewRes(
        Long id,
        String title,
        List<QuestionDto> questions,
        LocalDateTime createdAt
) {}