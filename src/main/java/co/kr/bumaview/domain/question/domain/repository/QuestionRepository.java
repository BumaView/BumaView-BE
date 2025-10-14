package co.kr.bumaview.domain.question.domain.repository;

import co.kr.bumaview.domain.question.domain.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long>, JpaSpecificationExecutor<Question> {

    long count();

    Page<Question> findByQuestionContaining(String query, Pageable pageable);

    @Query(value = "SELECT * FROM questions ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Question findRandomQuestion();

    List<Question> findByCategory(String category);

    @Query("""
        SELECT q FROM Question q
        WHERE (:category IS NULL OR q.category = :category)
          AND (:company IS NULL OR q.company = :company)
          AND (:year IS NULL OR q.year = :year)
    """)
    List<Question> findFilteredQuestions(
            @Param("category") String category,
            @Param("company") String company,
            @Param("year") Integer year
    );

}
