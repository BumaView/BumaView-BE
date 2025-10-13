package co.kr.bumaview.domain.bookmark.presentation.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FolderResponse {
    private Long folderId;
    private String name;
}