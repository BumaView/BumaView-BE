package co.kr.bumaview.domain.bookmark.presentation.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BookmarkResponse {
    private Long bookmarkId;
    private Long questionId;
    private Long folderId;
}