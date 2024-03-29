package com.muesli.music.domain.track;

import com.muesli.music.domain.album.AlbumInfo;
import com.muesli.music.domain.artist.ArtistInfo;
import com.muesli.music.domain.genre.GenreInfo;
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
        private final String musicUrl;
        private final String description;
        private final String composer;
        private final String lyricser;
        private final String arranger;
        private final Long adult;
        private final Long isTitle;
        private final AlbumInfo.Main albumInfo;
        private final LyricsInfo lyricsInfo;
        private final ArtistInfo.Main artistInfo;

        public Main(Track track, ArtistInfo.Main artistInfo) {
            this.id = track.getId();
            this.name = track.getName();
            this.number = track.getNumber();
            this.duration = track.getDuration();
            this.musicUrl = track.getUrl();
            this.description = track.getDescription();
            this.composer = track.getComposer();
            this.lyricser = track.getLyricser();
            this.arranger = track.getArranger();
            this.adult = track.getAdult();
            this.isTitle = track.getIsTitle();
            this.lyricsInfo = null;
            this.artistInfo = artistInfo;
            this.albumInfo = new AlbumInfo.Main(track.getAlbum(), null);
        }

        public Main(Track track, ArtistInfo.Main artistInfo, LyricsInfo lyricsInfo) {
            this.id = track.getId();
            this.name = track.getName();
            this.number = track.getNumber();
            this.duration = track.getDuration();
            this.musicUrl = track.getUrl();
            this.description = track.getDescription();
            this.composer = track.getComposer();
            this.lyricser = track.getLyricser();
            this.arranger = track.getArranger();
            this.adult = track.getAdult();
            this.isTitle = track.getIsTitle();
            this.lyricsInfo = lyricsInfo;
            this.artistInfo = artistInfo;
            this.albumInfo = new AlbumInfo.Main(track.getAlbum(), null);
        }
    }

    /*
            아티스트, 앨범 내 트랙 리스트 조회를 위한 클래스 입니다.
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
        private final Long isTitle;
        private final String musicUrl;
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
            this.isTitle = track.getIsTitle();
            this.musicUrl = track.getUrl();
            this.albumInfo = albumBasicInfo;
            this.artistInfo = artistInfo;
        }
    }

    /**
     * 일반 트랙 리스트 정보
     */
    @Getter
    @ToString
    public static class TrackListInfo {
        private final Long id;
        private final String name;
        private final Long number;
        private final Long duration;
        private final String artistsLegacy;
        private final String description;
        private final String image;
        private final Long adult;
        private final Long isTitle;
        private final String musicUrl;

        private final Long artistId;
        private final String artistName;

        private final Long albumId;
        private final String albumName;
        private final String albumImage;

        public TrackListInfo(Map<String, Object> track) {
            var adult = track.get("adult") != null ? Long.parseLong(String.valueOf(track.get("adult"))) : null;
            var duration = track.get("duration") != null ? Long.parseLong(String.valueOf(track.get("duration"))) : null;
            var isTitle = track.get("isTitle") != null ? Long.parseLong(String.valueOf(track.get("isTitle"))) : null;

            this.id = Long.parseLong(String.valueOf(track.get("id")));
            this.name = (String) track.get("name");
            this.number = Long.parseLong(String.valueOf(track.get("number")));
            this.duration = duration;
            this.artistsLegacy = (String) track.get("artistsLegacy");
            this.description = (String) track.get("description");
            this.image = (String) track.get("image");
            this.adult = adult;
            this.isTitle = isTitle;
            this.musicUrl = (String) track.get("url");

            this.artistId = Long.parseLong(String.valueOf(track.get("artistId")));
            this.artistName = (String) track.get("artistName");

            this.albumId = Long.parseLong(String.valueOf(track.get("albumId")));
            this.albumName = (String) track.get("albumName");
            this.albumImage = (String) track.get("albumImage");
        }
    }

    @Getter
    @ToString
    public static class LyricsInfo {
        private final Long id;
        private final List<LyricsDetailInfo> lyrics;

        public LyricsInfo(Long id, List<LyricsDetailInfo> lyricsDetailInfo) {
            this.id = id;
            this.lyrics = lyricsDetailInfo;
        }
    }

    @Getter
    @ToString
    public static class LyricsDetailInfo {
        private final String timeline;
        private final String text;
        private final String textOriginal;
        private final String textPron;

        public LyricsDetailInfo(String timeline, String text, String textOriginal, String textPron) {
            this.timeline = timeline;
            this.text = text;
            this.textOriginal = textOriginal;
            this.textPron = textPron;
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
        private final Long isTitle;
        private final String musicUrl;

        private final Long artistId;
        private final String artistName;

        private final Long albumId;
        private final String albumName;
        private final String albumImage;

        private final String date;

        public ChartInfo(Map<String, Object> track) {
            var rank = track.get("rank") != null ? Long.parseLong(String.valueOf(track.get("rank"))) : null;
            var wave = track.get("wave") != null ? Long.parseLong(String.valueOf(track.get("wave"))) : null;
            var adult = track.get("adult") != null ? Long.parseLong(String.valueOf(track.get("adult"))) : null;
            var duration = track.get("duration") != null ? Long.parseLong(String.valueOf(track.get("duration"))) : null;
            var isTitle = track.get("isTitle") != null ? Long.parseLong(String.valueOf(track.get("isTitle"))) : null;

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
            this.isTitle = isTitle;
            this.musicUrl = (String) track.get("url");

            this.artistId = Long.parseLong(String.valueOf(track.get("artistId")));
            this.artistName = (String) track.get("artistName");

            this.albumId = Long.parseLong(String.valueOf(track.get("albumId")));
            this.albumName = (String) track.get("albumName");
            this.albumImage = (String) track.get("albumImage");

            this.date = (String) track.get("date");
        }
    }

    @Getter
    @ToString
    public static class ChartLayoutInfo {
        private final List<GenreInfo.Main> genreList;
        private final List<String> dateList;

        public ChartLayoutInfo(Map<String, Object> chartLayout, String type) {
            this.genreList = (List<GenreInfo.Main>) chartLayout.get("genreList");
            switch (type) {
                case "week" : this.dateList = (List<String>) chartLayout.get("weekDate"); break;
                case "month" : this.dateList = (List<String>) chartLayout.get("monthDate"); break;
                default: this.dateList = null;
            }
        }
    }


    @Getter
    @ToString
    public static class SearchLyricsInfo {
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

        public SearchLyricsInfo(Map<String, Object> lyrics) {
            var adult = lyrics.get("adult") != null ? Long.parseLong(String.valueOf(lyrics.get("adult"))) : null;
            var lyricsText = lyrics.get("lyricsText") != null && !lyrics.get("lyricsText").equals("null") ? String.valueOf(lyrics.get("lyricsText")) : "";
            var lyricsTextPron = lyrics.get("lyricsTextPron") != null && !lyrics.get("lyricsTextPron").equals("null") ? String.valueOf(lyrics.get("lyricsTextPron")) : "";
            var lyricsTextOriginal = lyrics.get("lyricsTextOriginal") != null && !lyrics.get("lyricsTextOriginal").equals("null") ? String.valueOf(lyrics.get("lyricsTextOriginal")) : "";
            var isTitle = lyrics.get("isTitle") != null ? Long.parseLong(String.valueOf(lyrics.get("isTitle"))) : null;

            this.id = Long.parseLong(String.valueOf(lyrics.get("id")));
            this.name = (String) lyrics.get("name");
            this.lyricsText = lyricsText.replaceAll("/", "");
            this.lyricsTextPron = lyricsTextPron.replaceAll("/", "");
            this.lyricsTextOriginal = lyricsTextOriginal.replaceAll("/", "");

            this.adult = adult;
            this.isTitle = isTitle;
            this.musicUrl = (String) lyrics.get("url");

            this.artistId = Long.parseLong(String.valueOf(lyrics.get("artistId")));
            this.artistName = (String) lyrics.get("artistName");

            this.albumId = Long.parseLong(String.valueOf(lyrics.get("albumId")));
            this.albumName = (String) lyrics.get("albumName");
            this.albumImage = (String) lyrics.get("albumImage");

        }
    }


}
