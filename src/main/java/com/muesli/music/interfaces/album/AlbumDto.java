package com.muesli.music.interfaces.album;

import com.muesli.music.interfaces.like.LikeDto;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

public class AlbumDto {

    @Getter
    @Builder
    @ToString
    public static class Main {
        private final Long id;
        private final String albumCode;
        private final String name;
        private final String releaseDate;
        private final String originalName;
        private final String image;
        private final String description;
        private final LikeDto.LikeInfo likeInfo;
        private final List<TrackInfo> trackList;
    }

    @Getter
    @Builder
    @ToString
    public static class TrackInfo {
        private final Long id;
        private final String name;
        private final String original;
        private final Long number;
        private final Long duration;
        private final String artistsLegacy;
        private final String url;
        private final String description;
        private final String image;
        private final String composer;
        private final String lyricser;
        private final String arranger;
        private final Long adult;
        private final LikeDto.LikeInfo likeInfo;
    }
}
