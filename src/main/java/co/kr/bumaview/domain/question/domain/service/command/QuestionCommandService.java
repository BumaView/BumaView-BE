package co.kr.bumaview.domain.question.domain.service.command;

import co.kr.bumaview.domain.question.domain.Question;
import co.kr.bumaview.domain.question.domain.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionCommandService {

    private final QuestionRepository questionRepository;

    public Question createQuestion(String q, String authorId) {
        Question question = new Question(q, authorId);
        return questionRepository.save(question);
    }
}
