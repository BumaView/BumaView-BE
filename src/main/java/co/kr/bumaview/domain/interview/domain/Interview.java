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

    private String userId;

    @Column(name = "interview_name")
    private String interviewName;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "question_id")
    private List<Question> questions = new ArrayList<>();

    @Column(columnDefinition = "TEXT", name="answer_text")
    private String answer;

    private LocalDateTime createdAt = LocalDateTime.now();

    public Interview(String interviewName, List<Question> questions) {
        this.interviewName = interviewName;
        this.questions = questions;
    }
}