package com.muesli.music.domain.track;

import com.muesli.music.domain.album.AlbumInfo;
import com.muesli.music.domain.artist.ArtistInfo;
import com.muesli.music.domain.genre.GenreInfo;
import com.muesli.music.domain.track.lyrics.Lyrics;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.Map;


public class TrackInfo {

    @Getter
    @ToString
    public static class Main {
        private final Long id;
        private final String name;
        private final Long number;
        private final Long duration;
        private final String url;
        private final String description;
        private final String composer;
        private final String lyricser;
        private final String arranger;
        private final Long adult;
        private final AlbumInfo.Main albumInfo;
        private final LyricsInfo lyricsInfo;
        private final ArtistInfo.Main artistInfo;

        public Main(Track track, ArtistInfo.Main artistInfo) {
            this.id = track.getId();
            this.name = track.getName();
            this.number = track.getNumber();
            this.duration = track.getDuration();
            this.url = track.getUrl();
            this.description = track.getDescription();
            this.composer = track.getComposer();
            this.lyricser = track.getLyricser();
            this.arranger = track.getArranger();
            this.adult = track.getAdult();
            this.lyricsInfo = null;
            this.artistInfo = artistInfo;
            this.albumInfo = new AlbumInfo.Main(track.getAlbum(), null);
        }

        public Main(Track track, ArtistInfo.Main artistInfo, LyricsInfo lyricsInfo) {
            this.id = track.getId();
            this.name = track.getName();
            this.number = track.getNumber();
            this.duration = track.getDuration();
            this.url = track.getUrl();
            this.description = track.getDescription();
            this.composer = track.getComposer();
            this.lyricser = track.getLyricser();
            this.arranger = track.getArranger();
            this.adult = track.getAdult();
            this.lyricsInfo = lyricsInfo;
            this.artistInfo = artistInfo;
            this.albumInfo = new AlbumInfo.Main(track.getAlbum(), null);
        }
    }

    /*
            아티스트 내 앨범 리스트 조회를 위한 클래스 입니다.
         */
    @Getter
    @ToString
    public static class TrackBasicInfo {
        private final Long id;
        private final String name;
        private final Long number;
        private final Long duration;
        private final String description;
        private final String composer;
        private final String lyricser;
        private final String arranger;
        private final Long adult;
        private final ArtistInfo.Main artistInfo;
        private final AlbumInfo.AlbumBasicInfo albumInfo;

        public TrackBasicInfo(Track track, AlbumInfo.AlbumBasicInfo albumBasicInfo, ArtistInfo.Main artistInfo) {
            this.id = track.getId();
            this.name = track.getName();
            this.number = track.getNumber();
            this.duration = track.getDuration();
            this.description = track.getDescription();
            this.composer = track.getComposer();
            this.lyricser = track.getLyricser();
            this.arranger = track.getArranger();
            this.adult = track.getAdult();
            this.albumInfo = albumBasicInfo;
            this.artistInfo = artistInfo;
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

    @Getter
    @ToString
    public static class ChartInfo {
        private final Long rank;
        private final Long wave;
        private final Long id;
        private final String name;
        private final Long number;
        private final Long duration;
        private final String artistsLegacy;
        private final String description;
        private final String image;
        private final Long adult;

        private final Long artistId;
        private final String artistName;

        private final Long albumId;
        private final String albumName;
        private final String albumImage;

        public ChartInfo(Map<String, Object> track) {
            var rank = track.get("rank") != null ? Long.parseLong(String.valueOf(track.get("rank"))) : null;
            var wave = track.get("wave") != null ? Long.parseLong(String.valueOf(track.get("wave"))) : null;
            var adult = track.get("adult") != null ? Long.parseLong(String.valueOf(track.get("adult"))) : null;
            var duration = track.get("duration") != null ? Long.parseLong(String.valueOf(track.get("duration"))) : null;

            this.rank = rank;
            this.wave = wave;
            this.id = Long.parseLong(String.valueOf(track.get("id")));
            this.name = (String) track.get("name");
            this.number = Long.parseLong(String.valueOf(track.get("number")));
            this.duration = duration;
            this.artistsLegacy = (String) track.get("artistsLegacy");
            this.description = (String) track.get("description");
            this.image = (String) track.get("image");
            this.adult = adult;

            this.artistId = Long.parseLong(String.valueOf(track.get("artistId")));
            this.artistName = (String) track.get("artistName");

            this.albumId = Long.parseLong(String.valueOf(track.get("albumId")));
            this.albumName = (String) track.get("albumName");
            this.albumImage = (String) track.get("albumImage");
        }
    }

    @Getter
    @ToString
    public static class ChartLayoutInfo {
        private final List<GenreInfo.Main> genreList;
        private final List<String> yearDate;
        private final List<String> monthDate;
        private final List<String> weekDate;

        public ChartLayoutInfo(Map<String, Object> chartLayout) {
            this.genreList = (List<GenreInfo.Main>) chartLayout.get("genreList");
            this.yearDate = (List<String>) chartLayout.get("yearDate");
            this.monthDate = (List<String>) chartLayout.get("monthDate");
            this.weekDate = (List<String>) chartLayout.get("weekDate");
        }
    }
}
