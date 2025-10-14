package co.kr.bumaview.domain.dashboard.presentation.dto;

import java.util.List;

public record DashboardSummary(
        int totalQuestions,
        int totalBookmarks,
        int totalInterviews,
        List<RecentActivity> recentActivity
) {
    public record RecentActivity(String title) {}
}