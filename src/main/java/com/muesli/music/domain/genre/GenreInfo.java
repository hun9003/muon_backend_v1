package com.muesli.music.domain.genre;

import lombok.Getter;
import lombok.ToString;

public class GenreInfo {

    @Getter
    @ToString
    public static class Main {
        private final Long id;
        private final String name;
        private final String image;
        private final String displayName;

        public Main(Genre genre) {
            this.id = genre.getId();
            this.name = genre.getName();
            this.image = genre.getImage();
            this.displayName = genre.getDisplayName();
        }
    }
}
