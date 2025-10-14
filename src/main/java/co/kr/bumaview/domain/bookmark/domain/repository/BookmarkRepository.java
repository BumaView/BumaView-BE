package co.kr.bumaview.domain.bookmark.domain.repository;

import co.kr.bumaview.domain.bookmark.domain.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    List<Bookmark> findByFolderId(Long folderId);
    long countByUserId(String userId);
}
