package co.kr.bumaview.domain.interview.service;

import co.kr.bumaview.domain.interview.domain.Interview;
import co.kr.bumaview.domain.interview.domain.repository.InterviewRepository;
import co.kr.bumaview.domain.interview.presentation.dto.req.CreateInterviewReq;
import co.kr.bumaview.domain.interview.presentation.dto.res.CreateInterviewRes;
import co.kr.bumaview.domain.question.domain.Question;
import co.kr.bumaview.domain.question.domain.repository.QuestionRepository;
import co.kr.bumaview.domain.question.presentation.dto.req.QuestionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InterviewService {

    private final QuestionRepository questionRepository;
    private final InterviewRepository interviewRepository;

    public CreateInterviewRes createInterview(CreateInterviewReq req) {
        List<Question> allQuestions = questionRepository.findByCategory(req.category());

        if (allQuestions.isEmpty()) {
            throw new IllegalArgumentException("해당 카테고리의 질문이 없습니다.");
        }

        // 요청 count 만큼 랜덤 추출
        Collections.shuffle(allQuestions);
        List<Question> selectedQuestions = allQuestions.stream()
                .limit(req.count())
                .toList();

        Interview interview = interviewRepository.save(new Interview(req.title(), selectedQuestions));

        List<QuestionDto> questionDtos = selectedQuestions.stream()
                .map(q -> new QuestionDto(q.getId(), q.getQuestion()))
                .toList();

        return new CreateInterviewRes(
                interview.getId(),
                interview.getInterviewName(),
                questionDtos,
                interview.getCreatedAt()
        );
    }
}