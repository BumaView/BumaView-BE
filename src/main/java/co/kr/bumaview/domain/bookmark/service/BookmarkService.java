package co.kr.bumaview.domain.bookmark.service;

import co.kr.bumaview.domain.bookmark.domain.Bookmark;
import co.kr.bumaview.domain.bookmark.domain.BookmarkFolder;
import co.kr.bumaview.domain.bookmark.domain.repository.BookmarkFolderRepository;
import co.kr.bumaview.domain.bookmark.domain.repository.BookmarkRepository;
import co.kr.bumaview.domain.bookmark.presentation.dto.req.CreateBookmarkRequest;
import co.kr.bumaview.domain.bookmark.presentation.dto.req.CreateFolderRequest;
import co.kr.bumaview.domain.bookmark.presentation.dto.res.BookmarkResponse;
import co.kr.bumaview.domain.bookmark.presentation.dto.res.FolderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookmarkService {
    private final BookmarkFolderRepository bookmarkFolderRepository;
    private final BookmarkRepository bookmarkRepository;

    public FolderResponse createFolder(CreateFolderRequest request, String userId) {
        BookmarkFolder folder = bookmarkFolderRepository.save(
                new BookmarkFolder(request.getName(), userId)
        );
        return new FolderResponse(folder.getId(), folder.getName());
    }

    public BookmarkResponse createBookmark(CreateBookmarkRequest request, String userId) {
        Bookmark bookmark = bookmarkRepository.save(
                new Bookmark(request.getQuestionId(), request.getFolderId(), userId)
        );
        return new BookmarkResponse(bookmark.getId(), bookmark.getQuestionId(), bookmark.getFolderId());
    }
}
