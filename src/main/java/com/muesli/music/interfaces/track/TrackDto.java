package com.muesli.music.interfaces.track;

import com.muesli.music.interfaces.like.LikeDto;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class TrackDto {

    @Getter
    @Builder
    @ToString
    public static class Main {
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
        private final LyricsInfo lyricsInfo;
        private final LikeDto.LikeInfo likeInfo;
    }

    @Getter
    @Builder
    @ToString
    public static class LyricsInfo {
        private final Long id;
        private final String text;
    }
}
