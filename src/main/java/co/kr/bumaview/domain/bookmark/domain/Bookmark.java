package co.kr.bumaview.domain.bookmark.domain;

import co.kr.bumaview.domain.question.domain.Question;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "bookmark_items")
public class Bookmark {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long folderId;
    private String userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", insertable = false, updatable = false)
    private Question question;

    @Column(name = "question_id")
    private Long questionId;


    public Bookmark(Long questionId, Long folderId, String userId) {
        this.questionId = questionId;
        this.folderId = folderId;
        this.userId = userId;
    }
}
