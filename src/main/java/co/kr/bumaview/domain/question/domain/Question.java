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

    private String question;

    @Column(columnDefinition = "json")
    private String tad; //-> json

    private String company;

    private Long year;

    private String category;

    private String field;

    @JoinColumn(name = "author_id")
    private String authorId;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public Question(String question, String authorId) {
        this.question = question;
        this.authorId = authorId;
    }
}
