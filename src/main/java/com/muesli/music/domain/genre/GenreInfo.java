package com.muesli.music.domain.genre;

import com.muesli.music.domain.album.AlbumInfo;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

public class GenreInfo {

    @Getter
    @ToString
    public static class Main {
        private final Long id;
        private final String name;
        private final String image;
        private final String displayName;
        private final String title;
        private final String description;
        private final int views;
        private List<AlbumInfo.AlbumListInfo> albumList;

        public Main(Genre genre) {
            this.id = genre.getId();
            this.name = genre.getName();
            this.image = genre.getImage();
            this.displayName = genre.getDisplayName();
            this.title = genre.getTitle();
            this.description = genre.getDescription();
            this.views = genre.getViews();
        }

        public void setAlbumList(List<AlbumInfo.AlbumListInfo> albumList) {
            this.albumList = albumList;
        }
    }
}
