package co.kr.bumaview.domain.interview.presentation;

import co.kr.bumaview.domain.question.domain.Question;
import co.kr.bumaview.domain.question.domain.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/interviews")
@RequiredArgsConstructor
@PreAuthorize("hasRole('USER')")
public class InterviewController {

    private final QuestionRepository questionRepository;

    @GetMapping("/random")
    public Question getRandomInterview() {
        return questionRepository.findRandomQuestion();
    }
}
