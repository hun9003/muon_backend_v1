package com.muesli.music.domain.search;

import com.muesli.music.domain.search.history.History;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class SearchCommand {

    @Getter
    @Setter
    @Builder
    @ToString
    public static class SearchRequest {
        private final String keyword;
        private final String type;

        private int trackCount;
        private int albumCount;
        private int artistCount;
        private int lyricsCount;
    }

    @Getter
    @Builder
    @ToString
    public static class saveSearchHistory {
        private final String keyword;
        private final String ip;
        private final int resultCount;
        private final Long userId;

        public History toEntity(Long userId) {
            return History.builder()
                    .keyword(keyword)
                    .ip(ip)
                    .resultCount(resultCount)
                    .userId(userId)
                    .build();
        }
    }

}
