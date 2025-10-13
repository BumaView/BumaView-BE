package co.kr.bumaview.domain.question.domain.repository;

import co.kr.bumaview.domain.question.domain.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    Page<Question> findByQuestionContaining(String query, Pageable pageable);
}
