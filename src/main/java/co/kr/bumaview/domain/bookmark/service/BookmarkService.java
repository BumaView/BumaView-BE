package co.kr.bumaview.domain.bookmark.service;

import co.kr.bumaview.domain.bookmark.domain.Bookmark;
import co.kr.bumaview.domain.bookmark.domain.BookmarkFolder;
import co.kr.bumaview.domain.bookmark.domain.repository.BookmarkFolderRepository;
import co.kr.bumaview.domain.bookmark.domain.repository.BookmarkRepository;
import co.kr.bumaview.domain.bookmark.presentation.dto.req.CreateBookmarkRequest;
import co.kr.bumaview.domain.bookmark.presentation.dto.req.CreateFolderRequest;
import co.kr.bumaview.domain.bookmark.presentation.dto.res.BookmarkFolderResponse;
import co.kr.bumaview.domain.bookmark.presentation.dto.res.BookmarkResponse;
import co.kr.bumaview.domain.bookmark.presentation.dto.res.FolderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        return new BookmarkResponse(
                bookmark.getId(),
                bookmark.getQuestionId(),
                bookmark.getQuestion() != null ? bookmark.getQuestion().getQuestion() : null
        );
    }


    @Transactional(readOnly = true)
    public BookmarkFolderResponse getFolderWithBookmarks(Long folderId) {
        BookmarkFolder folder = bookmarkFolderRepository.findById(folderId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 폴더입니다."));

        // bookmarkRepository에서 folderId로 직접 조회
        List<Bookmark> bookmarkList = bookmarkRepository.findByFolderId(folderId);

        List<BookmarkResponse> bookmarks = bookmarkList.stream()
                .map(b -> new BookmarkResponse(
                        b.getId(),
                        b.getQuestionId(),
                        b.getQuestion() != null ? b.getQuestion().getQuestion() : null
                ))
                .toList();

        return BookmarkFolderResponse.builder()
                .folderId(folder.getId())
                .name(folder.getName())
                .bookmarks(bookmarks)
                .build();
    }

    public List<BookmarkFolderResponse> getAllFolders() {
        return bookmarkFolderRepository.findAll().stream()
                .map(f -> BookmarkFolderResponse.builder()
                        .folderId(f.getId())
                        .name(f.getName())
                        .bookmarks(List.of()) // 전체 조회 시에는 북마크 정보는 비워둠
                        .build())
                .toList();
    }

}
