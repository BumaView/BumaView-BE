package co.kr.bumaview.domain.interview.domain;

import co.kr.bumaview.domain.question.domain.Question;
import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Type;

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

    @Column(name = "interview_name")
    private String interviewName;

    @Column(name = "user_id")
    private String userId;

    @ManyToMany
    @JoinTable(
            name = "mock_interview_questions",
            joinColumns = @JoinColumn(name = "interview_id"),
            inverseJoinColumns = @JoinColumn(name = "question_id")
    )
    private List<Question> questions = new ArrayList<>();

    @Type(JsonType.class)
    @Column(columnDefinition = "json")
    private List<AnswerRecord> answers;

    private LocalDateTime createdAt = LocalDateTime.now();

    public Interview(String title, List<Question> questions, String userId) {
        this.interviewName = title;
        this.userId = userId;
        this.questions = questions != null ? new ArrayList<>(questions) : new ArrayList<>();
        this.answers = new ArrayList<>();
    }

    public void addAnswer(Long questionId, String answer, int timeSpent) {
        if (this.answers == null) this.answers = new ArrayList<>();
        this.answers.add(new AnswerRecord(questionId, answer, timeSpent, LocalDateTime.now()));
    }
}