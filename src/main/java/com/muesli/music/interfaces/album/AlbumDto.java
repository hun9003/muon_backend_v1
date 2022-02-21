package com.muesli.music.interfaces.album;

import com.muesli.music.interfaces.like.LikeDto;
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
        private final String albumCode;
        private final String name;
        private final String releaseDate;
        private final String originalName;
        private final String image;
        private final String description;
        private final LikeDto.LikeInfo likeInfo;
        private final List<TrackInfo> trackList;
    }

    /**
     * 앨범 리스트 개별 정보
     */
    @Getter
    @Builder
    @ToString
    public static class AlbumInfo {
        private final Long id;
        private final String albumCode;
        private final String name;
        private final String releaseDate;
        private final String originalName;
        private final String image;
        private final String description;
        private final LikeDto.LikeInfo likeInfo;
    }

    /**
     * 트랙 정보
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
        private final String image;
        private final String composer;
        private final String lyricser;
        private final String arranger;
        private final Long adult;
        private final LikeDto.LikeInfo likeInfo;
    }

    /**
     * 앨범 리스트
     */
    @Getter
    @Builder
    @ToString
    public static class AlbumList {
        private final String type = "Album";
        private final List<AlbumInfo> list;
    }
}
