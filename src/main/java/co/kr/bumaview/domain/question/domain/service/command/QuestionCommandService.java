package co.kr.bumaview.domain.question.domain.service.command;

import co.kr.bumaview.domain.question.domain.Question;
import co.kr.bumaview.domain.question.domain.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionCommandService {

    private final QuestionRepository questionRepository;

    public Question createQuestion(String q) {
        Question question = new Question(q);
        return questionRepository.save(question);
    }
}
