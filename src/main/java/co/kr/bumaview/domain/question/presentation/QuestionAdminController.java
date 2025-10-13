package co.kr.bumaview.domain.question.presentation;

import co.kr.bumaview.domain.question.domain.service.command.QuestionCommandService;
import co.kr.bumaview.domain.question.presentation.dto.req.DeleteAllQuestionsRequest;
import co.kr.bumaview.domain.question.presentation.dto.req.QuestionRequest;
import co.kr.bumaview.domain.question.presentation.dto.req.UpdateQuestionRequest;
import co.kr.bumaview.domain.question.presentation.dto.res.*;
import co.kr.bumaview.domain.user.domain.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/questions")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class QuestionAdminController {

    private final QuestionCommandService questionCommandService;

    @PostMapping
    public ResponseEntity<QuestionResponse> handleQuestions(
            @Valid @RequestBody QuestionRequest requestDto,
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
    public ResponseEntity<QuestionSheetResponse> handleQuestionsFromSheet(
            @RequestBody String googleSheetUrl,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ){
        Long total = questionCommandService.createQuestionsFromGoogleSheet(googleSheetUrl, userDetails.getUserId());
        return ResponseEntity.ok().body(new QuestionSheetResponse("등록되었습니다", Map.of("total", total)));
    }

    @PatchMapping("/{question_id}")
    public ResponseEntity<UpdateQuestionResponse> updateQuestion(
            @PathVariable("question_id") Long questionId,
            @RequestBody UpdateQuestionRequest requestDto,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ){
        questionCommandService.updateQuestion(questionId, requestDto.getQ(), userDetails.getUserId());
        UpdateQuestionResponse response = new UpdateQuestionResponse(
                questionId,
                "질문이 수정되었습니다."
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{question_id}")
    public ResponseEntity<DeleteQuestionResponse> deleteQuestion(
            @PathVariable("question_id") Long questionId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ){
        questionCommandService.deleteQuestion(questionId, userDetails.getUserId());

        DeleteQuestionResponse response = new DeleteQuestionResponse(
                questionId,
                "질문이 삭제되었습니다."
        );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<DeleteAllQuestionsRes> deleteAllQuestions(
            @RequestBody DeleteAllQuestionsRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        questionCommandService.deleteAllQuestion(request.questionIds(), userDetails.getUserId());

        DeleteAllQuestionsRes res = new DeleteAllQuestionsRes(
                request.questionIds(),
                "선택한 질문이 삭제되었습니다."
        );

        return ResponseEntity.ok(res);
    }


}
