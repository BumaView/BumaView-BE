package co.kr.bumaview.domain.bookmark.service;

import co.kr.bumaview.domain.bookmark.domain.BookmarkFolder;
import co.kr.bumaview.domain.bookmark.domain.repository.BookmarkFolderRepository;
import co.kr.bumaview.domain.bookmark.presentation.dto.req.CreateFolderRequest;
import co.kr.bumaview.domain.bookmark.presentation.dto.res.FolderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookmarkService {
    private final BookmarkFolderRepository bookmarkFolderRepository;

    public FolderResponse createFolder(CreateFolderRequest request, String userId) {
        BookmarkFolder folder = bookmarkFolderRepository.save(
                new BookmarkFolder(request.getName(), userId)
        );
        return new FolderResponse(folder.getId(), folder.getName());
    }
}
