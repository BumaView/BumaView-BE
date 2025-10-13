package co.kr.bumaview.domain.question.presentation.dto.res;

import java.util.List;

public record DeleteAllQuestionsRes(List<Long> ids, String message) { }

