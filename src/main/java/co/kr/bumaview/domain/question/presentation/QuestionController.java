package co.kr.bumaview.domain.question.presentation;

import co.kr.bumaview.domain.question.domain.Question;
import co.kr.bumaview.domain.question.domain.repository.QuestionRepository;
import co.kr.bumaview.domain.question.domain.service.query.QuestionQueryService;
import co.kr.bumaview.domain.question.presentation.dto.req.QuestionFilterRequest;
import co.kr.bumaview.domain.question.presentation.dto.res.QuestionPageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionQueryService questionQueryService;
    private final QuestionRepository questionRepository;

    @GetMapping
    public ResponseEntity<Page<QuestionPageResponse>> getQuestionLists(
            @PageableDefault(size = 100) Pageable pageable
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

    @GetMapping(params = "query")
    public ResponseEntity<?> filterQuestion(
            @RequestParam("query") String query,
            @PageableDefault(size = 100, sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        // 검색 쿼리 적용
        Page<Question> questions = questionRepository.findByQuestionContaining(query, pageable);

        // Page<Question>를 DTO로 변환
        Page<QuestionPageResponse> response = questions.map(q -> new QuestionPageResponse(
                q.getId(),
                q.getQuestion(),
                q.getCompany(),
                q.getYear(),
                q.getCategory(),
                q.getTag(),
                q.getAuthorId()
        ));

        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<?> filterQuestions(
            @RequestBody QuestionFilterRequest request,
            @PageableDefault(size = 100, sort = "id", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        Page<Question> questions = questionQueryService.filterQuestions(request, pageable);

        Page<QuestionPageResponse> response = questions.map(q -> new QuestionPageResponse(
                q.getId(),
                q.getQuestion(),
                q.getCompany(),
                q.getYear(),
                q.getCategory(),
                q.getTag(),
                q.getAuthorId()
        ));

        return ResponseEntity.ok(response);
    }
}
