package co.kr.bumaview.domain.interview.domain.repository;

import co.kr.bumaview.domain.interview.domain.Interview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InterviewRepository extends JpaRepository<Interview, Long> {
    long countByUserId(String userId);

    List<Interview> findTop5ByUserIdOrderByCreatedAtDesc(String userId);
}
