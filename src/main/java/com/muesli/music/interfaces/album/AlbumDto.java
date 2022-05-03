package com.muesli.music.interfaces.album;

import com.muesli.music.common.util.Constant;
import com.muesli.music.interfaces.artist.ArtistDto;
import com.muesli.music.interfaces.track.TrackDto;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

public class AlbumDto {

    /**
     * 앨범 상세페이지 내용
     */
    @Getter
    @Builder
    @ToString
    public static class Main {
        private final Long id;
        private final String name;
        private final String releaseDate;
        private final String originalName;
        private final String image;
        private final String description;
        private final ArtistDto.AlbumArtistInfo artistInfo;
        private final List<TrackDto.AlbumTrackInfo> trackList;
    }

    /**
     * 앨범 리스트 개별 정보
     */
    @Getter
    @Builder
    @ToString
    public static class AlbumInfo {
        private final Long id;
        private final String name;
        private final String releaseDate;
        private final String originalName;
        private final String image;
        private final String description;
        private final ArtistDto.TrackArtistInfo artistInfo;
    }

    /**
     * 앨범 리스트
     */
    @Getter
    @ToString
    public static class AlbumList {
        private final String type;
        private final List<AlbumInfo> list;

        public AlbumList(List<AlbumInfo> list) {
            this.type = Constant.Item.ALBUM;
            this.list = list;
        }
    }

    /**
     * 트랙에게 제공하는 앨범 정보
     */
    @Getter
    @ToString
    @Builder
    public static class TrackAlbumInfo {
        private final Long id;
        private final String name;
        private final String image;
    }

    /**
     * 아티스트에게 제공하는 앨범 정보
     */
    @Getter
    @Builder
    @ToString
    public static class ArtistAlbumInfo {
        private final Long id;
        private final String name;
        private final String releaseDate;
        private final String originalName;
        private final String image;
        private final String description;
    }

    /**
     * 최신 앨범 정보
     */
    @Getter
    @ToString
    @Builder
    public static class NewestAlbumInfo {
        private final Long id;
        private final String name;
        private final String releaseDate;
        private final String originalName;
        private final String image;

        private final Long artistId;
        private final String artistName;
    }

    @Getter
    @ToString
    @Builder
    public static class NewestAlbumList {
        private final String type;
        private final List<NewestAlbumInfo> list;

        public NewestAlbumList(String type, List<NewestAlbumInfo> list) {
            this.type = type;
            this.list = list;
        }
    }

    /**
     * 앨범 검색 결과 정보
     */
    @Getter
    @ToString
    @Builder
    public static class AlbumSearchInfo {
        private final Long id;
        private final String name;
        private final String releaseDate;
        private final String originalName;
        private final String image;

        private final Long artistId;
        private final String artistName;
    }

    /**
     * 장르별 앨범 정보
     */
    @Getter
    @ToString
    @Builder
    public static class GenreAlbumInfo {
        private final Long id;
        private final String name;
        private final String releaseDate;
        private final String originalName;
        private final String image;

        private final Long artistId;
        private final String artistName;
    }

    /**
     * 채널별 앨범 정보
     */
    @Getter
    @ToString
    @Builder
    public static class ChannelAlbumInfo {
        private final Long id;
        private final String name;
        private final String releaseDate;
        private final String originalName;
        private final String image;

        private final Long artistId;
        private final String artistName;
    }
}
