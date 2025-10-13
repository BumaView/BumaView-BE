package co.kr.bumaview.domain.bookmark.domain.repository;

import co.kr.bumaview.domain.bookmark.domain.BookmarkFolder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkFolderRepository extends JpaRepository<BookmarkFolder, Long> {
}
