package com.muesli.music.interfaces.artist;

import com.muesli.music.domain.artist.ArtistInfo;
import com.muesli.music.interfaces.album.AlbumDto;
import com.muesli.music.interfaces.like.LikeDto;
import com.muesli.music.interfaces.track.TrackDto;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import java.util.List;

public class ArtistDto {

    /**
     * 아티스트 상세 페이지 정보
     */
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
        private final com.muesli.music.domain.artist.ArtistInfo.BiosInfo biosInfo;
        private final LikeDto.LikeInfo likeInfo;
        private final List<AlbumDto.ArtistAlbumInfo> albumList;
        private final List<TrackDto.ArtistTrackInfo> trackList;
    }

    /**
     * 아티스트 리스트 정보
     */
    @Getter
    @Builder
    @ToString
    public static class ArtistInfo {
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
    }

    /**
     * 아티스트 리스트
     */
    @Getter
    @ToString
    public static class ArtistList {
        private final String type;
        private final List<ArtistInfo> list;

        public ArtistList(List<ArtistInfo> list) {
            this.type = "Artist";
            this.list = list;
        }
    }

    /**
     * 트랙에게 제공하는 아티스트 정보
     */
    @Getter
    @ToString
    @Builder
    public static class TrackArtistInfo {
        private final Long id;
        private final String name;
        private final String originalName;
        private final String englishName;
    }
}
