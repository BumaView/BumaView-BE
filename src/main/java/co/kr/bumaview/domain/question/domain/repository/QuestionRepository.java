package co.kr.bumaview.domain.question.domain.repository;

import co.kr.bumaview.domain.question.domain.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface QuestionRepository extends JpaRepository<Question, Long>, JpaSpecificationExecutor<Question> {

    Page<Question> findByQuestionContaining(String query, Pageable pageable);

    @Query(value = "SELECT * FROM questions ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Question findRandomQuestion();

}
