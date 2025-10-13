package co.kr.bumaview.domain.bookmark.domain.repository;

import co.kr.bumaview.domain.bookmark.domain.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
}
