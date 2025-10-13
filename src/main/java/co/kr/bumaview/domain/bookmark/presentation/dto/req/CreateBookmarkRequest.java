package co.kr.bumaview.domain.bookmark.presentation.dto.req;

import lombok.Getter;

@Getter
public class CreateBookmarkRequest {
    private Long questionId;
    private Long folderId;
}
