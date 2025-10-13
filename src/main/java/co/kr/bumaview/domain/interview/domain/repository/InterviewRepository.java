package co.kr.bumaview.domain.interview.domain.repository;

import co.kr.bumaview.domain.interview.domain.Interview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterviewRepository extends JpaRepository<Interview, Long> {
}
