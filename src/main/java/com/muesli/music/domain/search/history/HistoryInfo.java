package com.muesli.music.domain.search.history;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class HistoryInfo {
    private Long id;
    private String keyword;
    private String ip;
    private Long userId;
    private int resultCount;

    public HistoryInfo(History history) {
        this.id = history.getId();
        this.keyword = history.getKeyword();
        this.ip = history.getIp();
        this.userId = history.getUserId();
        this.resultCount = history.getResultCount();
    }
}
