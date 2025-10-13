package co.kr.bumaview.domain.interview.presentation;

import co.kr.bumaview.domain.interview.presentation.dto.req.CreateInterviewReq;
import co.kr.bumaview.domain.interview.presentation.dto.res.CreateInterviewRes;
import co.kr.bumaview.domain.interview.service.InterviewService;
import co.kr.bumaview.domain.question.domain.Question;
import co.kr.bumaview.domain.question.domain.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PostMapping
    public ResponseEntity<CreateInterviewRes> createInterview(@RequestBody CreateInterviewReq req) {
        return ResponseEntity.ok(interviewService.createInterview(req));
    }
}
