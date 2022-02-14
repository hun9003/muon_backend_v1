package com.muesli.music.interfaces.album;

import com.muesli.music.domain.album.Album;
import com.muesli.music.domain.album.AlbumInfo;
import com.muesli.music.domain.like.Like;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.sql.Timestamp;
import java.time.ZonedDateTime;
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
        private final String artistId;
        private final String description;
        private final AlbumInfo.LikeInfo likeInfo;
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
    }

    @Getter
    @Builder
    @ToString
    public static class LikeInfo {
        private final Long id;
        private final ZonedDateTime createdAt;
    }
}
