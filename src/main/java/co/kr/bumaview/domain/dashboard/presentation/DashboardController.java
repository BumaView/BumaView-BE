package co.kr.bumaview.domain.dashboard.presentation;

import co.kr.bumaview.domain.dashboard.domain.service.DashboardService;
import co.kr.bumaview.domain.dashboard.presentation.dto.DashboardSummary;
import co.kr.bumaview.domain.user.domain.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/dashboard")
@PreAuthorize("isAuthenticated()")
@RequiredArgsConstructor
public class DashboardController {
    private final DashboardService dashboardService;

    @GetMapping
    public DashboardSummary getDashboard(
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        return dashboardService.getDashboardSummary(user.getUserId());
    }
}
