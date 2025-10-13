package co.kr.bumaview.domain.bookmark.presentation.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class BookmarkFolderResponse {
    private Long folderId;
    private String name;
    private List<BookmarkResponse> bookmarks; // 폴더 조회 시만 채움
}
