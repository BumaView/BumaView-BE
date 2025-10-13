package co.kr.bumaview.domain.question.presentation;

import co.kr.bumaview.domain.question.domain.service.command.QuestionCommandService;
import co.kr.bumaview.domain.question.presentation.dto.req.QuestionRequest;
import co.kr.bumaview.domain.question.presentation.dto.res.QuestionResponse;
import co.kr.bumaview.domain.user.domain.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/questions")
@RequiredArgsConstructor
public class QuestionAdminController {

    private final QuestionCommandService questionCommandService;

    @PostMapping
    public ResponseEntity<QuestionResponse> handleQuestions(
            @RequestBody QuestionRequest requestDto,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        List<QuestionResponse.RegisteredQuestion> registered = requestDto.getQuestions().stream()
                .map(q -> {
                    var saved = questionCommandService.createQuestion(q.getQ(), userDetails.getUserId());
                    return new QuestionResponse.RegisteredQuestion(saved.getId(), saved.getQuestion());
                })
                .toList();

        return ResponseEntity.ok(new QuestionResponse("등록되었습니다", registered));
    }

    @PostMapping("/sheets")
    public ResponseEntity<QuestionResponse> handleQuestionsFromSheet(
            @RequestBody String googleSheetUrl,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ){
        questionCommandService.createQuestionsFromGoogleSheet(googleSheetUrl, userDetails.getUserId());
        return ResponseEntity.ok(new QuestionResponse("등록되었습니다", List.of()));
    }
}
