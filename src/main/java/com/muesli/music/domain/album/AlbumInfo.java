package com.muesli.music.domain.album;

import lombok.Getter;
import lombok.ToString;

public class AlbumInfo {

    @Getter
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

        public Main(Album album) {
            this.id = album.getId();
            this.album_code = album.getAlbum_code();
            this.name = album.getName();
            this.release_date = album.getRelease_date();
            this.original_name = album.getOriginal_name();
            this.image = album.getImage();
            this.artist_id = album.getArtist_id();
            this.description = album.getDescription();
        }
    }
}
