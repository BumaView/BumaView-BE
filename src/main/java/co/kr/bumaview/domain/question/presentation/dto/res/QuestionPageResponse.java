package co.kr.bumaview.domain.question.presentation.dto.res;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record QuestionPageResponse(
        Long id,
        String question,
        String company,
        Long year,
        String category,
        String tag
) {}