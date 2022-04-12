package com.muesli.music.domain.search;

import com.muesli.music.domain.album.AlbumInfo;
import com.muesli.music.domain.artist.ArtistInfo;
import com.muesli.music.domain.track.TrackInfo;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

public class SearchInfo {
    // 통합검색
    @Getter
    @ToString
    public static class searchAll {
        private final SearchTrack searchTrack;
        private final SearchAlbum searchAlbum;
        private final SearchArtist searchArtist;
        private final SearchLyrics searchLyrics;

        public searchAll(SearchTrack searchTrack, SearchAlbum searchAlbum, SearchArtist searchArtist, SearchLyrics searchLyrics) {
            this.searchTrack = searchTrack;
            this.searchAlbum = searchAlbum;
            this.searchArtist = searchArtist;
            this.searchLyrics = searchLyrics;
        }
    }

    // 곡 검색
    @Getter
    @ToString
    public static class SearchTrack {
        private final int count;
        private final String order;
        private final List<TrackInfo.SearchInfo> trackList;

        public SearchTrack(int count, String order, List<TrackInfo.SearchInfo> trackList) {
            this.count = count;
            this.order = order;
            this.trackList = trackList;
        }
    }

    // 앨범 검색
    @Getter
    @ToString
    public static class SearchAlbum {
        private final int count;
        private final String order;
        private final List<AlbumInfo.SearchInfo> albumList;

        public SearchAlbum(int count, String order, List<AlbumInfo.SearchInfo> albumList) {
            this.count = count;
            this.order = order;
            this.albumList = albumList;
        }
    }

    // 아티스트 검색
    @Getter
    @ToString
    public static class SearchArtist {
        private final int count;
        private final String order;
        private final List<ArtistInfo.SearchInfo> artistList;

        public SearchArtist(int count, String order, List<ArtistInfo.SearchInfo> artistList) {
            this.count = count;
            this.order = order;
            this.artistList = artistList;
        }
    }

    // 가사 검색
    @Getter
    @ToString
    public static class SearchLyrics {
        private final int count;
        private final String order;
        private final List<TrackInfo.SearchLyricsInfo> lyricsList;

        public SearchLyrics(int count, String order, List<TrackInfo.SearchLyricsInfo> lyricsList) {
            this.count = count;
            this.order = order;
            this.lyricsList = lyricsList;
        }
    }
}
