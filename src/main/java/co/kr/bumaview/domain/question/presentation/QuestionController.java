package co.kr.bumaview.domain.question.presentation;

import co.kr.bumaview.domain.question.domain.service.query.QuestionQueryService;
import co.kr.bumaview.domain.question.presentation.dto.res.QuestionPageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionQueryService questionQueryService;

    @GetMapping
    public ResponseEntity<Page<QuestionPageResponse>> getQuestionLists(
            @PageableDefault(size = 10) Pageable pageable
    ) {
        Page<QuestionPageResponse> questions = questionQueryService.getQuestions(pageable);
        return ResponseEntity.ok(questions);
    }

    @GetMapping("/{question_id}")
    public ResponseEntity<QuestionPageResponse> getOneQuestion(
            @PathVariable("question_id") Long questionId
    ) {
        QuestionPageResponse question = questionQueryService.getOneQuestion(questionId);
        return ResponseEntity.ok(question);
    }

}
