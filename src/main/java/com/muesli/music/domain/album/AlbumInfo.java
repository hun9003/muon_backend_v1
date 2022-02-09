package com.muesli.music.domain.album;

import com.muesli.music.domain.like.Like;
import com.muesli.music.domain.track.Track;
import lombok.Getter;
import lombok.ToString;

import java.time.ZonedDateTime;
import java.util.List;

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
        private final List<TrackInfo> trackList;

        public Main(Album album, List<TrackInfo> trackInfoList) {
            this.id = album.getId();
            this.album_code = album.getAlbum_code();
            this.name = album.getName();
            this.release_date = album.getRelease_date();
            this.original_name = album.getOriginal_name();
            this.image = album.getImage();
            this.artist_id = album.getArtist_id();
            this.description = album.getDescription();
            this.trackList = trackInfoList;
        }
    }

    @Getter
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


        public TrackInfo(Track track) {
            this.id = track.getId();
            this.name = track.getName();
            this.original = track.getOriginal();
            this.number = track.getNumber();
            this.duration = track.getDuration();
            this.artists_legacy = track.getArtists_legacy();
            this.url = track.getUrl();
            this.description = track.getDescription();
            this.image = track.getImage();
            this.composer = track.getComposer();
            this.lyricser = track.getLyricser();
            this.arranger = track.getArranger();
            this.adult = track.getAdult();
        }
    }

    @Getter
    @ToString
    public static class LikeInfo {
        private final Long id;
        private final Long likeable_id;
        private final Long user_id;
        private final String likeable_type;
        private final ZonedDateTime created_at;

        public LikeInfo(Like like) {
            this.id = like.getId();
            this.likeable_id = like.getLikeableId();
            this.user_id = like.getUserId();
            this.likeable_type = like.getLikeableType();
            this.created_at = like.getCreated_at();
        }
    }
}
