package co.kr.bumaview.domain.dashboard.domain.service;

import co.kr.bumaview.domain.bookmark.domain.repository.BookmarkRepository;
import co.kr.bumaview.domain.dashboard.presentation.dto.DashboardSummary;
import co.kr.bumaview.domain.interview.domain.repository.InterviewRepository;
import co.kr.bumaview.domain.question.domain.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {
    private final QuestionRepository questionRepository;
    private final BookmarkRepository bookmarkRepository;
    private final InterviewRepository interviewRepository;

    public DashboardSummary getDashboardSummary(String userId) {
        int totalQuestions = (int) questionRepository.count();
        int totalBookmarks = (int) bookmarkRepository.countByUserId(userId);
        int totalInterviews = (int) interviewRepository.countByUserId(userId);

        List<DashboardSummary.RecentActivity> recent = interviewRepository.findTop5ByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(i -> new DashboardSummary.RecentActivity(i.getInterviewName()))
                .toList();

        return new DashboardSummary(totalQuestions, totalBookmarks, totalInterviews, recent);
    }
}
