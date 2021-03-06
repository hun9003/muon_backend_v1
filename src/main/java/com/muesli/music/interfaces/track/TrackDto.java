package com.muesli.music.interfaces.track;

import com.muesli.music.common.util.Constant;
import com.muesli.music.domain.track.TrackCommand;
import com.muesli.music.interfaces.album.AlbumDto;
import com.muesli.music.interfaces.artist.ArtistDto;
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
        private final Long number;
        private final Long duration;
        private final String musicUrl;
        private final String description;
        private final String composer;
        private final String lyricser;
        private final String arranger;
        private final Long adult;
        private final Long isTitle;
        private final LyricsInfo lyricsInfo;
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
        private final String albumImage;
        private final String description;
        private final Long adult;
        private final Long isTitle;
        private final String musicUrl;

        private final Long artistId;
        private final String artistName;

        private final Long albumId;
        private final String albumName;
    }

    /**
     * 트랙 리스트
     */
    @Getter
    @ToString
    public static class TrackInfoList {
        private final String type;
        private final List<TrackInfo> list;

        public TrackInfoList(List<TrackInfo> list) {
            this.type = Constant.Item.TRACK;
            this.list = list;
        }
    }

    /**
     * 가사 정보
     */
    @Getter
    @Builder
    @ToString
    public static class LyricsInfo {
        private final Long id;
        private final List<LyricsDetailInfo> lyrics;
    }

    @Getter
    @Builder
    @ToString
    public static class LyricsDetailInfo {
        private final String timeline;
        private final String text;
        private final String textOriginal;
        private final String textPron;
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
        private final String albumImage;
        private final String description;
        private final Long adult;
        private final Long isTitle;
        private final String musicUrl;
        private final Long number;
        private final Long duration;

        private final Long artistId;
        private final String artistName;

        private final Long albumId;
        private final String albumName;
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
        private final Long number;
        private final Long duration;
        private final String description;
        private final Long adult;
        private final Long isTitle;
        private final String musicUrl;
        private final ArtistDto.TrackArtistInfo artistInfo;
        private final AlbumDto.TrackAlbumInfo albumInfo;
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
        private final Long adult;
        private final Long isTitle;
        private final String musicUrl;
        private final ArtistDto.TrackArtistInfo artistInfo;
        private final AlbumDto.TrackAlbumInfo albumInfo;
    }

    /**
     * 트랙 순위 정보
     */
    @Getter
    @ToString
    @Builder
    public static class TrackRankInfo {
        private final Long id;
        private final String name;
        private final Long rank;
        private final Long wave;
        private final String albumImage;
        private final String description;
        private final Long adult;
        private final Long isTitle;
        private final String musicUrl;
        private final Long number;
        private final Long duration;

        private final Long artistId;
        private final String artistName;

        private final Long albumId;
        private final String albumName;
    }

    /**
     * 곡 순위 리스트
     */
    @Getter
    @ToString
    @Builder
    public static class TrackRankList {
        private final String date;
        private final String type;
        private final Long genre;
        private final com.muesli.music.domain.track.TrackInfo.ChartLayoutInfo layout;
        private final List<TrackRankInfo> trackList;

        public TrackRankList(String date, String type, Long genre, com.muesli.music.domain.track.TrackInfo.ChartLayoutInfo layout, List<TrackRankInfo> trackList) {
            this.date = date;
            this.type = type;
            this.genre = genre;
            this.layout = layout;
            this.trackList = trackList;
        }

        public TrackCommand.SearchRankCommand toCommand() {
            return TrackCommand.SearchRankCommand.builder()
                    .date(date)
                    .genre(genre)
                    .type(type).build();
        }
    }

    /**
     * 가사 검색 결과
     */
    @Getter
    @ToString
    @Builder
    public static class LyricsSearchInfo {
        private final Long id;
        private final String name;
        private final Long adult;
        private final Long isTitle;
        private final String musicUrl;

        private final String lyricsText;
        private final String lyricsTextPron;
        private final String lyricsTextOriginal;

        private final Long artistId;
        private final String artistName;

        private final Long albumId;
        private final String albumName;
        private final String albumImage;
    }

}
