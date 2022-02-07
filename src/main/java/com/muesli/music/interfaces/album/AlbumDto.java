package com.muesli.music.interfaces.album;

import com.muesli.music.domain.album.Album;
import com.muesli.music.domain.like.Like;
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
        private final String album_code;
        private final String name;
        private final String release_date;
        private final String original_name;
        private final String image;
        private final String artist_id;
        private final String description;
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
        private final String artists_legacy;
        private final String url;
        private final String description;
        private final String image;
        private final String composer;
        private final String lyricser;
        private final String arranger;
        private final Long adult;
    }
}
