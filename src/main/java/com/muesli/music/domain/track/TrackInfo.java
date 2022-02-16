package com.muesli.music.domain.track;

import com.muesli.music.domain.like.LikeInfo;
import com.muesli.music.domain.track.lyrics.Lyrics;
import lombok.Getter;
import lombok.ToString;


public class TrackInfo {

    @Getter
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
        private LikeInfo likeInfo;

        public Main(Track track) {
            this.id = track.getId();
            this.original = track.getOriginal();
            this.name = track.getName();
            this.number = track.getNumber();
            this.duration = track.getDuration();
            this.artistsLegacy = track.getArtistsLegacy();
            this.url = track.getUrl();
            this.description = track.getDescription();
            this.image = track.getImage();
            this.composer = track.getComposer();
            this.lyricser = track.getLyricser();
            this.arranger = track.getArranger();
            this.adult = track.getAdult();
            this.lyricsInfo = null;
        }

        public Main(Track track, LyricsInfo lyricsInfo, LikeInfo likeInfo) {
            this.id = track.getId();
            this.original = track.getOriginal();
            this.name = track.getName();
            this.number = track.getNumber();
            this.duration = track.getDuration();
            this.artistsLegacy = track.getArtistsLegacy();
            this.url = track.getUrl();
            this.description = track.getDescription();
            this.image = track.getImage();
            this.composer = track.getComposer();
            this.lyricser = track.getLyricser();
            this.arranger = track.getArranger();
            this.adult = track.getAdult();
            this.likeInfo = likeInfo;
            this.lyricsInfo = lyricsInfo;
        }

        public void setLikeInfo(LikeInfo likeInfo) {
            this.likeInfo = likeInfo;
        }
    }

    @Getter
    @ToString
    public static class LyricsInfo {
        private final Long id;
        private final String text;

        public LyricsInfo(Lyrics lyrics) {
            this.id = lyrics.getId();
            this.text = lyrics.getText();
        }
    }


}
