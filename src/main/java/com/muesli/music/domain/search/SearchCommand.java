package com.muesli.music.domain.search;

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
}
