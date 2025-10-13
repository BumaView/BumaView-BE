package co.kr.bumaview.domain.question.domain.service.query;

import co.kr.bumaview.domain.question.domain.repository.QuestionRepository;
import co.kr.bumaview.domain.question.presentation.dto.res.QuestionPageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionQueryService {
    private final QuestionRepository questionRepository;

    public Page<QuestionPageResponse> getQuestions(Pageable pageable) {
        return questionRepository.findAll(pageable)
                .map(q -> new QuestionPageResponse(
                        q.getId(),
                        q.getQuestion(),
                        q.getCompany(),
                        q.getYear(),
                        q.getCategory(),
                        q.getTag()
                ));
    }

    public QuestionPageResponse getOneQuestion(Long questionId) {
        return questionRepository.findById(questionId)
                .map(q -> new QuestionPageResponse(
                        q.getId(),
                        q.getQuestion(),
                        q.getCompany(),
                        q.getYear(),
                        q.getCategory(),
                        q.getTag()
                ))
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 질문입니다."));
    }
}
