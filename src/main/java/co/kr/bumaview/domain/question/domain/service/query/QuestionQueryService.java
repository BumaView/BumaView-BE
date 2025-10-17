package co.kr.bumaview.domain.question.domain.service.query;

import co.kr.bumaview.domain.question.domain.Question;
import co.kr.bumaview.domain.question.domain.repository.QuestionRepository;
import co.kr.bumaview.domain.question.presentation.dto.req.QuestionFilterRequest;
import co.kr.bumaview.domain.question.presentation.dto.res.QuestionPageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;


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
                        q.getTag(),
                        q.getAuthorId()
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
                        q.getTag(),
                        q.getAuthorId()
                ))
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 질문입니다."));
    }

    public Page<Question> filterQuestions(QuestionFilterRequest request, Pageable pageable) {
        return questionRepository.findAll((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (request.getCompany() != null) {
                predicates.add(cb.equal(root.get("company"), request.getCompany()));
            }
            if (request.getYear() != null) {
                predicates.add(cb.equal(root.get("year"), request.getYear()));
            }
            if (request.getCategory() != null) {
                predicates.add(cb.equal(root.get("category"), request.getCategory()));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        }, pageable);
    }
}
