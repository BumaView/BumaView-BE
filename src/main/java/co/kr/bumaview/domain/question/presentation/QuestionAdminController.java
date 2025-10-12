package co.kr.bumaview.domain.question.presentation;

import co.kr.bumaview.domain.question.presentation.dto.req.QuestionRequest;
import co.kr.bumaview.domain.question.presentation.dto.res.QuestionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/questions")
public class QuestionAdminController {

    @PostMapping
    public ResponseEntity<QuestionResponse> handleQuestions(
            @RequestBody QuestionRequest requestDto
    ) {
        // 질문 처리 로직
        return ResponseEntity.ok(new QuestionResponse("등록되었습니다"));
    }
}
