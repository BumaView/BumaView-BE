package co.kr.bumaview.domain.question.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name="questions")
@Getter
@NoArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String question;

    @Column(columnDefinition = "json")
    private String tag; //-> json

    private String company;

    private Long year;

    private String category;

    private String field;

    @Column(name = "author_id")
    private String authorId;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public Question(String question, String authorId) {
        this.question = question;
        this.authorId = authorId;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public static Question of(String question, String authorId, String company, Long year, String category) {
        Question q = new Question();
        q.question = question;
        q.authorId = authorId;
        q.company = company;
        q.year = year;
        q.category = category;
        q.createdAt = LocalDateTime.now();
        q.updatedAt = LocalDateTime.now();
        return q;
    }
}
