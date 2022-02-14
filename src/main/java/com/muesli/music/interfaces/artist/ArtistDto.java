package com.muesli.music.interfaces.artist;

import com.muesli.music.interfaces.like.LikeDto;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import java.util.List;

public class ArtistDto {

    @Getter
    @Builder
    @ToString
    public static class Main {
        private final Long id;
        private final String name;
        private final String originalName;
        private final String englishName;
        private final String image;
        private final String birthday;
        private final String country;
        private final Long debut;
        private final String agency;
        private final String label;
        private final int views;
        private final String imageSmall;
        private final LikeDto.LikeInfo likeInfo;
        private final List<AlbumInfo> albumList;
    }

    @Getter
    @Builder
    @ToString
    public static class AlbumInfo {
        private final Long id;
        private final String albumCode;
        private final String name;
        private final String releaseDate;
        private final String originalName;
        private final String image;
        private final String description;
        private final LikeDto.LikeInfo likeInfo;
    }

}
