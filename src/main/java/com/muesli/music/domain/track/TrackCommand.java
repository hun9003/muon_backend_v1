package com.muesli.music.domain.track;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class TrackCommand {

    @Getter
    @Builder
    @ToString
    public static class SearchRankCommand {
        private final String type;
        private final String date;
        private final Long genre;
    }
}
