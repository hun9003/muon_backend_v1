package com.muesli.music.domain.track;

import com.muesli.music.domain.album.Album;
import com.muesli.music.domain.album.AlbumInfo;
import com.muesli.music.domain.artist.ArtistInfo;
import com.muesli.music.domain.like.LikeInfo;
import com.muesli.music.domain.track.lyrics.Lyrics;
import lombok.Builder;
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
        private final AlbumInfo.Main albumInfo;
        private final LyricsInfo lyricsInfo;
        private final ArtistInfo.Main artistInfo;

        public Main(Track track, ArtistInfo.Main artistInfo) {
            this.id = track.getId();
            this.original = track.getOriginal();
            this.name = track.getName();
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
            this.lyricsInfo = null;
            this.artistInfo = artistInfo;
            this.albumInfo = new AlbumInfo.Main(track.getAlbum(), null);
        }

        public Main(Track track, ArtistInfo.Main artistInfo, LyricsInfo lyricsInfo) {
            this.id = track.getId();
            this.original = track.getOriginal();
            this.name = track.getName();
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
        private final String artistsLegacy;
        private final String description;
        private final String image;
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
            this.artistsLegacy = track.getArtistsLegacy();
            this.description = track.getDescription();
            this.image = track.getImage();
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
    public static class RankInfo {
        private final int rank;
        private final Long wave;
        private final Long id;
        private final String name;
        private final Long number;
        private final Long duration;
        private final String artistsLegacy;
        private final String description;
        private final String image;
        private final Long adult;
        private final ArtistInfo.Main artistInfo;
        private final AlbumInfo.AlbumBasicInfo albumInfo;

        public RankInfo(Map<String, Object> track, AlbumInfo.AlbumBasicInfo albumBasicInfo, ArtistInfo.Main artistInfo) {
            this.rank = Integer.parseInt((String) track.get("rank"));
            this.wave = (Long) track.get("wave");
            this.id = (Long) track.get("id");
            this.name = (String) track.get("id");
            this.number = (Long) track.get("id");
            this.duration = (Long) track.get("id");
            this.artistsLegacy = (String) track.get("id");
            this.description = (String) track.get("id");
            this.image = (String) track.get("id");
            this.adult = (Long) track.get("id");
            this.albumInfo = albumBasicInfo;
            this.artistInfo = artistInfo;
        }
    }

}
