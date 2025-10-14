package co.kr.bumaview.domain.interview.service;

import co.kr.bumaview.domain.interview.domain.AnswerRecord;
import co.kr.bumaview.domain.interview.domain.Interview;
import co.kr.bumaview.domain.interview.domain.repository.InterviewRepository;
import co.kr.bumaview.domain.interview.presentation.dto.req.CreateInterviewReq;
import co.kr.bumaview.domain.interview.presentation.dto.req.WriteAnswerReq;
import co.kr.bumaview.domain.interview.presentation.dto.res.CreateInterviewRes;
import co.kr.bumaview.domain.interview.presentation.dto.res.InterviewSummaryRes;
import co.kr.bumaview.domain.interview.presentation.dto.res.WriteAnswerRes;
import co.kr.bumaview.domain.question.domain.Question;
import co.kr.bumaview.domain.question.domain.repository.QuestionRepository;
import co.kr.bumaview.domain.question.presentation.dto.req.QuestionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InterviewService {

    private final QuestionRepository questionRepository;
    private final InterviewRepository interviewRepository;

    public CreateInterviewRes createInterview(CreateInterviewReq req, String userId) {
        List<Question> allQuestions = questionRepository.findByCategory(req.category());

        if (allQuestions.isEmpty()) {
            throw new IllegalArgumentException("해당 카테고리의 질문이 없습니다.");
        }

        // 요청 count 만큼 랜덤 추출
        Collections.shuffle(allQuestions);
        List<Question> selectedQuestions = allQuestions.stream()
                .limit(req.count())
                .toList();

        Interview interview = interviewRepository.save(new Interview(req.title(), selectedQuestions, userId));

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


    @Transactional
    public WriteAnswerRes writeAnswer(Long interviewId, WriteAnswerReq req, String userId) {
        Interview interview = interviewRepository.findById(interviewId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 면접 세션입니다."));

        if (!interview.getUserId().equals(userId)) {
            throw new AccessDeniedException("면접 세션에 접근할 수 있는 권한이 없습니다.");
        }

        interview.addAnswer(req.getQuestionId(), req.getAnswer(), req.getTimeSpent());

        return new WriteAnswerRes(
                interview.getId(),
                req.getQuestionId(),
                req.getAnswer(),
                req.getTimeSpent(),
                java.time.LocalDateTime.now()
        );
    }

    @Transactional(readOnly = true)
    public InterviewSummaryRes getInterviewSummary(Long interviewId, String userId) {
        Interview interview = interviewRepository.findById(interviewId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 면접 세션입니다."));

        if (!interview.getUserId().equals(userId)) {
            throw new AccessDeniedException("자신의 면접만 조회할 수 있습니다.");
        }

        List<AnswerRecord> answers = interview.getAnswers();
        if (answers == null || answers.isEmpty()) {
            throw new IllegalStateException("아직 답변이 없습니다.");
        }

        int totalQuestions = answers.size();
        int totalTimeSpent = answers.stream()
                .mapToInt(AnswerRecord::getTimeSpent)
                .sum();
        double average = Math.round((double) totalTimeSpent / totalQuestions * 10) / 10.0;

        return new InterviewSummaryRes(
                interviewId,
                new InterviewSummaryRes.Summary(totalQuestions, totalTimeSpent, average, answers)
        );
    }
}