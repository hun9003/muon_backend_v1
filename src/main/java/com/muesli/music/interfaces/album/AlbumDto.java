package com.muesli.music.interfaces.album;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class AlbumDto {

    @Getter
    @Builder
    @ToString
    public static class Main {
        private final Long id;
        private final String album_code;
        private final String name;
        private final String release_date;
        private final String original_name;
        private final String image;
        private final String artist_id;
        private final String description;
    }
}
