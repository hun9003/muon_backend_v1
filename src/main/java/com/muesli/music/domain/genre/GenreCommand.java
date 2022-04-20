package com.muesli.music.domain.genre;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class GenreCommand {

    @Getter
    @Builder
    @ToString
    public static class GenreRequest {
        private final String keyword;
        private final Long genreId;
        private final String type;

    }
}
