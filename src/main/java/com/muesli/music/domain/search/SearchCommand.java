package com.muesli.music.domain.search;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class SearchCommand {

    @Getter
    @Builder
    @ToString
    public static class SearchRequest {
        private final String keyword;
        private final String type;
    }
}
