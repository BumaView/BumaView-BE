package co.kr.bumaview.domain.interview.presentation;

import co.kr.bumaview.domain.interview.presentation.dto.req.CreateInterviewReq;
import co.kr.bumaview.domain.interview.presentation.dto.req.WriteAnswerReq;
import co.kr.bumaview.domain.interview.presentation.dto.res.CreateInterviewRes;
import co.kr.bumaview.domain.interview.presentation.dto.res.InterviewSummaryRes;
import co.kr.bumaview.domain.interview.presentation.dto.res.WriteAnswerRes;
import co.kr.bumaview.domain.interview.service.InterviewService;
import co.kr.bumaview.domain.question.domain.Question;
import co.kr.bumaview.domain.question.domain.repository.QuestionRepository;
import co.kr.bumaview.domain.question.presentation.dto.req.GetRandomQuestionReq;
import co.kr.bumaview.domain.question.presentation.dto.res.GetRandomQuestionRes;
import co.kr.bumaview.domain.user.domain.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("user/interviews")
@RequiredArgsConstructor
@PreAuthorize("hasRole('USER')")
public class InterviewController {

    private final QuestionRepository questionRepository;
    private final InterviewService interviewService;

    @GetMapping("/random")
    public Question getRandomInterview() {
        return questionRepository.findRandomQuestion();
    }

    @PostMapping("/random/filter")
    public ResponseEntity<GetRandomQuestionRes> getRandomQuestion(
            @RequestBody GetRandomQuestionReq req
    ) {
        return ResponseEntity.ok(interviewService.getRandomQuestion(req));
    }

    @PostMapping
    public ResponseEntity<CreateInterviewRes> createInterview(
            @RequestBody CreateInterviewReq req,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return ResponseEntity.ok(interviewService.createInterview(req, userDetails.getUserId()));
    }

    @GetMapping("/{interview_id}")
    public ResponseEntity<InterviewSummaryRes> getInterviewSummary(
            @PathVariable("interview_id") Long interviewId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        return ResponseEntity.ok(interviewService.getInterviewSummary(interviewId, userDetails.getUserId()));
    }

    @PostMapping("/{interview_id}")
    public ResponseEntity<WriteAnswerRes> writeAnswer(
            @PathVariable("interview_id") Long interviewId,
            @RequestBody WriteAnswerReq req,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        WriteAnswerRes response = interviewService.writeAnswer(interviewId, req, userDetails.getUserId());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{interview_id}/finish")
    public ResponseEntity<InterviewSummaryRes> finishInterview(
            @PathVariable("interview_id") Long interviewId,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ){
        return ResponseEntity.ok().body(
                interviewService.getInterviewSummary(interviewId, userDetails.getUserId())
        );
    }
}
