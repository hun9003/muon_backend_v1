package com.muesli.music.domain.search.keyword;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class KeywordInfo {
    private Long id;
    private String keyword;
    private int views;
    private int isPublic;

    public KeywordInfo(Keyword keyword) {
        this.id = keyword.getId();
        this.keyword = keyword.getKeyword();
        this.views = keyword.getViews();
        this.isPublic = keyword.getIsPublic();
    }
}
