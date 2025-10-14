package co.kr.bumaview.domain.question.presentation.dto.res;

public record GetRandomQuestionRes(
        Long id,
        String question,
        String category,
        String company,
        Integer year
) {}
