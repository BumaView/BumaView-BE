package co.kr.bumaview.domain.interview.presentation.dto.req;

public record CreateInterviewReq(
        String title,
        String category,
        int count
) {}