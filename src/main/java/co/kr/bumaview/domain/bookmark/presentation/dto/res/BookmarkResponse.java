package co.kr.bumaview.domain.bookmark.presentation.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BookmarkResponse {
    private Long bookmarkId;   // 북마크 PK
    private Long questionId;   // 질문 ID
    private String question; // 여기 Long → String으로 변경
}