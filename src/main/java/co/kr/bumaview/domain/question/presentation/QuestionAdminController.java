package co.kr.bumaview.domain.question.presentation;

import co.kr.bumaview.domain.question.domain.service.command.QuestionCommandService;
import co.kr.bumaview.domain.question.presentation.dto.req.QuestionRequest;
import co.kr.bumaview.domain.question.presentation.dto.res.QuestionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/questions")
@RequiredArgsConstructor
public class QuestionAdminController {

    private final QuestionCommandService questionCommandService;

    @PostMapping
    public ResponseEntity<QuestionResponse> handleQuestions(
            @RequestBody QuestionRequest requestDto
    ) {
        // 질문 처리 로직
        requestDto.getQuestions().forEach(q ->
                questionCommandService.createQuestion(q.getQ())
        );
        return ResponseEntity.ok(new QuestionResponse("등록되었습니다"));
    }
}
