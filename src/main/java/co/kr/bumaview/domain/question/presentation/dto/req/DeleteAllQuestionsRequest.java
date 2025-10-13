package co.kr.bumaview.domain.question.presentation.dto.req;

import java.util.List;

public record DeleteAllQuestionsRequest(List<Long> questionIds) { }
