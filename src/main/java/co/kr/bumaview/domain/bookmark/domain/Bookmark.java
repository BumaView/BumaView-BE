package co.kr.bumaview.domain.bookmark.domain;

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

    private Long questionId;
    private Long folderId;
    private String userId;

    public Bookmark(Long questionId, Long folderId, String userId) {
        this.questionId = questionId;
        this.folderId = folderId;
        this.userId = userId;
    }
}
