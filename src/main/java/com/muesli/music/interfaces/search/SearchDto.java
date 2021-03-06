package com.muesli.music.interfaces.search;

import com.muesli.music.common.exception.BaseException;
import com.muesli.music.common.response.ErrorCode;
import com.muesli.music.domain.search.SearchCommand;
import com.muesli.music.interfaces.album.AlbumDto;
import com.muesli.music.interfaces.artist.ArtistDto;
import com.muesli.music.interfaces.track.TrackDto;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class SearchDto {

    @Getter
    @Builder
    @ToString
    public static class SearchRequest {

        @NotNull(message = "키워드는 필수 값입니다.")
        @NotEmpty(message = "키워드는 필수 값입니다.")
        @Size(min=1, message = "키워드는 1자 이상 입력해야 합니다.")
        private final String keyword;
        private final String type;

        public SearchRequest(String keyword, String type) {
            if(keyword == null || keyword.trim().length() < 1)
                throw new BaseException(ErrorCode.EMPTY_SEARCH_KEYWORD);
            this.keyword = keyword;
            this.type = type;
        }

        public SearchCommand.SearchRequest toCommand() {
            return SearchCommand.SearchRequest.builder()
                    .keyword(keyword)
                    .type(type)
                    .build();
        }
    }
    @Getter
    @Builder
    @ToString
    public static class SearchAllResult {
        private final String keyword;
        private final String type;
        private final SearchTrackResult track;
        private final SearchAlbumResult album;
        private final SearchArtistResult artist;
//        private final SearchLyricsResult lyrics;

//        public SearchAllResult(String keyword, String type, SearchTrackResult track, SearchAlbumResult album, SearchArtistResult artist, SearchLyricsResult lyrics) {
        public SearchAllResult(String keyword, String type, SearchTrackResult track, SearchAlbumResult album, SearchArtistResult artist) {
            this.keyword = keyword;
            this.type = type;
            this.track = track;
            this.album = album;
            this.artist = artist;
//            this.lyrics = lyrics;
        }
    }

    @Getter
    @Builder
    @ToString
    public static class SearchTrackResult {
        private final String keyword;
        private final String type;
        private final int count;
        private final List<TrackDto.TrackInfo> trackList;

        public SearchTrackResult(String keyword, String type, int count, List<TrackDto.TrackInfo> trackList) {
            this.keyword = keyword;
            this.type = type;
            this.count = count;
            this.trackList = trackList;
        }
    }

    @Getter
    @Builder
    @ToString
    public static class SearchAlbumResult {
        private final String keyword;
        private final String type;
        private final int count;
        private final List<AlbumDto.AlbumInfo> albumList;

        public SearchAlbumResult(String keyword, String type, int count, List<AlbumDto.AlbumInfo> albumList) {
            this.keyword = keyword;
            this.type = type;
            this.count = count;
            this.albumList = albumList;
        }
    }

    @Getter
    @Builder
    @ToString
    public static class SearchArtistResult {
        private final String keyword;
        private final String type;
        private final int count;
        private final List<ArtistDto.ArtistInfo> artistList;

        public SearchArtistResult(String keyword, String type, int count, List<ArtistDto.ArtistInfo> artistList) {
            this.keyword = keyword;
            this.type = type;
            this.count = count;
            this.artistList = artistList;
        }
    }
//
//    @Getter
//    @Builder
//    @ToString
//    public static class SearchLyricsResult {
//        private final String keyword;
//        private final String type;
//        private final int count;
//        private final List<TrackDto.LyricsSearchInfo> lyricsList;
//
//        public SearchLyricsResult(String keyword, String type, int count, List<TrackDto.LyricsSearchInfo> lyricsList) {
//            this.keyword = keyword;
//            this.type = type;
//            this.count = count;
//            this.lyricsList = lyricsList;
//        }
//    }

    public static class SearchHistory {
        private final String keyword;
        private final String ip;
        private final int resultCount;

        public SearchHistory(String keyword, String ip, int resultCount) {
            this.keyword = keyword;
            this.ip = ip;
            this.resultCount = resultCount;
        }

        public SearchCommand.saveSearchHistory toCommand() {
            return SearchCommand.saveSearchHistory.builder()
                    .keyword(keyword)
                    .ip(ip)
                    .resultCount(resultCount)
                    .build();
        }
    }

    @Getter
    @Builder
    @ToString
    public static class SearchKeyword {
        private final String keyword;
    }
}
