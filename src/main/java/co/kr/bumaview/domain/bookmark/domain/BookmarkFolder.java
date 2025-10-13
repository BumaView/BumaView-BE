package co.kr.bumaview.domain.bookmark.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "bookmark_folders")
public class BookmarkFolder {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String userId;

    public BookmarkFolder(String name, String userId) {
        this.name = name;
        this.userId = userId;
    }
}