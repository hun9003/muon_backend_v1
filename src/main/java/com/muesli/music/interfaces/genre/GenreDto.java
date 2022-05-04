package com.muesli.music.interfaces.genre;

import com.muesli.music.domain.genre.GenreCommand;
import com.muesli.music.interfaces.album.AlbumDto;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

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

    @Getter
    @Builder
    @ToString
    public static class GenreInfo {
        private final Long id;
        private final String name;
        private final String image;
        private final String displayName;
        private final String title;
        private final String description;
        private final int views;
        private final List<AlbumDto.AlbumInfo> albumList;
    }
}
