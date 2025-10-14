package co.kr.bumaview.domain.question.presentation.dto.req;

public record GetRandomQuestionReq(
        String category,
        String company,
        Integer year
) {}