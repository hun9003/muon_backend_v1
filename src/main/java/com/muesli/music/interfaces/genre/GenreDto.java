package com.muesli.music.interfaces.genre;

import com.muesli.music.domain.genre.GenreCommand;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class GenreDto {
    @Getter
    @Builder
    @ToString
    public static class GenreRequest {
        private final String keyword;
        private final Long genreId;
        private final String type;

        public GenreCommand.GenreRequest toCommand() {
            return GenreCommand.GenreRequest.builder()
                    .genreId(genreId)
                    .keyword(keyword)
                    .type(type)
                    .build();
        }
    }
}
