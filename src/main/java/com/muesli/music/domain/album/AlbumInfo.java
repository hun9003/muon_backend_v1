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
        private final String albumCode;
        private final String name;
        private final String releaseDate;
        private final String originalName;
        private final String image;
        private final String artistId;
        private final String description;
        private final List<TrackInfo> trackList;
        private final LikeInfo likeInfo;

        public Main(Album album, List<TrackInfo> trackInfoList, LikeInfo likeInfo) {
            this.id = album.getId();
            this.albumCode = album.getAlbumCode();
            this.name = album.getName();
            this.releaseDate = album.getReleaseDate();
            this.originalName = album.getOriginalName();
            this.image = album.getImage();
            this.artistId = album.getArtistId();
            this.description = album.getDescription();
            this.trackList = trackInfoList;
            this.likeInfo = likeInfo;
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
        private final String artistsLegacy;
        private final String url;
        private final String description;
        private final String image;
        private final String composer;
        private final String lyricser;
        private final String arranger;
        private final Long adult;
        private LikeInfo likeInfo;


        public TrackInfo(Track track) {
            this.id = track.getId();
            this.name = track.getName();
            this.original = track.getOriginal();
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
        }

        public void setLikeInfo(LikeInfo likeInfo) {
            this.likeInfo = likeInfo;
        }
    }

    @Getter
    @ToString
    public static class LikeInfo {
        private final Long id;
//        private final Long likeable_id;
//        private final Long user_id;
//        private final String likeable_type;
        private final ZonedDateTime createdAt;

        public LikeInfo(Like like) {
            this.id = like.getId();
//            this.likeable_id = like.getLikeableId();
//            this.user_id = like.getUserId();
//            this.likeable_type = like.getLikeableType();
            this.createdAt = like.getCreated_at();
        }
    }
}
