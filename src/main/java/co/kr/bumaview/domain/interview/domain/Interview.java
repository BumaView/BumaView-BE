package co.kr.bumaview.domain.interview.domain;

import co.kr.bumaview.domain.question.domain.Question;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "mock_interviews")
@Getter
@RequiredArgsConstructor
public class Interview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "interview_id")
    private List<Question> questions = new ArrayList<>();

    private LocalDateTime createdAt = LocalDateTime.now();

    public Interview(String title, List<Question> questions) {
        this.title = title;
        this.questions = questions;
    }
}