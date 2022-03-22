package com.muesli.music.interfaces.track;

import com.muesli.music.interfaces.album.AlbumDto;
import com.muesli.music.interfaces.artist.ArtistDto;
import com.muesli.music.interfaces.like.LikeDto;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

public class TrackDto {

    /**
     * 트랙 상세페이지 내용
     */
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
        private final String composer;
        private final String lyricser;
        private final String arranger;
        private final Long adult;
        private final LyricsInfo lyricsInfo;
        private final LikeDto.LikeInfo likeInfo;
        private final ArtistDto.TrackArtistInfo artistInfo;
        private final AlbumDto.TrackAlbumInfo albumInfo;
    }

    /**
     * 트랙 리스트 개별 정보
     */
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
        private final String composer;
        private final String lyricser;
        private final String arranger;
        private final Long adult;
        private final LikeDto.LikeInfo likeInfo;
        private final ArtistDto.TrackArtistInfo artistInfo;
        private final AlbumDto.TrackAlbumInfo albumInfo;
    }

    /**
     * 가사 정보
     */
    @Getter
    @Builder
    @ToString
    public static class LyricsInfo {
        private final Long id;
        private final String text;
    }

    /**
     * 트랙 리스트
     */
    @Getter
    @ToString
    public static class TrackList {
        private final String type;
        private final List<TrackInfo> list;

        public TrackList(List<TrackInfo> list) {
            this.type = "Track";
            this.list = list;
        }
    }

    /**
     * 앨범에게 제공하는 트랙 정보
     */
    @Getter
    @ToString
    @Builder
    public static class AlbumTrackInfo {
        private final Long id;
        private final String name;
        private final String original;
        private final Long number;
        private final Long duration;
        private final String artistsLegacy;
        private final String url;
        private final String description;
        private final String image;
        private final Long adult;
        private final LikeDto.LikeInfo likeInfo;
        private final ArtistDto.TrackArtistInfo artistInfo;
    }

    /**
     * 아티스트에게 제공하는 트랙 정보
     */
    @Getter
    @ToString
    @Builder
    public static class ArtistTrackInfo {
        private final Long id;
        private final String name;
        private final String original;
        private final Long number;
        private final Long duration;
        private final String artistsLegacy;
        private final String url;
        private final String description;
        private final String image;
        private final Long adult;
        private final LikeDto.LikeInfo likeInfo;
        private final ArtistDto.TrackArtistInfo artistInfo;
    }

    /**
     * 플레이리스트에게 제공하는 트랙 정보
     */
    @Getter
    @Builder
    @ToString
    public static class PlaylistTrackInfo {
        private final Long id;
        private final String name;
        private final Long duration;
        private final String artistsLegacy;
        private final String image;
        private final Long adult;
        private final LikeDto.LikeInfo likeInfo;
        private final ArtistDto.TrackArtistInfo artistInfo;
        private final AlbumDto.TrackAlbumInfo albumInfo;
    }
}
