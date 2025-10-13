package co.kr.bumaview.domain.bookmark.presentation;

import co.kr.bumaview.domain.bookmark.presentation.dto.req.CreateBookmarkRequest;
import co.kr.bumaview.domain.bookmark.presentation.dto.req.CreateFolderRequest;
import co.kr.bumaview.domain.bookmark.presentation.dto.res.BookmarkResponse;
import co.kr.bumaview.domain.bookmark.presentation.dto.res.FolderResponse;
import co.kr.bumaview.domain.bookmark.service.BookmarkService;
import co.kr.bumaview.domain.user.domain.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bookmarks")
@PreAuthorize("hasRole('USER')")
@RequiredArgsConstructor
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @PostMapping("/folders")
    public ResponseEntity<FolderResponse> createFolder(
            @RequestBody CreateFolderRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        FolderResponse response = bookmarkService.createFolder(request, userDetails.getUserId());
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<BookmarkResponse> createBookmark(
            @RequestBody CreateBookmarkRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        BookmarkResponse response = bookmarkService.createBookmark(request, userDetails.getUserId());
        return ResponseEntity.ok(response);
    }
}
