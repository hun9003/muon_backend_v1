package com.muesli.music.domain.artist;

import com.muesli.music.domain.album.AlbumInfo;
import com.muesli.music.domain.artist.bios.Bios;
import com.muesli.music.domain.track.TrackInfo;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

public class ArtistInfo {

    @Getter
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
        private final List<AlbumInfo.AlbumBasicInfo> albumList;
        private final List<TrackInfo.TrackBasicInfo> trackList;
        private final BiosInfo biosInfo;

        public Main(Artist artist, BiosInfo biosInfo, List<AlbumInfo.AlbumBasicInfo> albumList, List<TrackInfo.TrackBasicInfo> trackList) {
            this.id = artist.getId();
            this.name = artist.getName();
            this.originalName = artist.getOriginalName();
            this.englishName = artist.getEnglishName();
            this.image = artist.getImage();
            this.birthday = artist.getBirthday();
            this.country = artist.getCountry();
            this.debut = artist.getDebut();
            this.agency = artist.getAgency();
            this.label = artist.getLabel();
            this.views = artist.getViews();
            this.imageSmall = artist.getImageSmall();
            this.albumList = albumList;
            this.trackList = trackList;
            this.biosInfo = biosInfo;
        }

        public Main(Artist artist) {
            this.id = artist.getId();
            this.name = artist.getName();
            this.originalName = artist.getOriginalName();
            this.englishName = artist.getEnglishName();
            this.image = artist.getImage();
            this.birthday = artist.getBirthday();
            this.country = artist.getCountry();
            this.debut = artist.getDebut();
            this.agency = artist.getAgency();
            this.label = artist.getLabel();
            this.views = artist.getViews();
            this.imageSmall = artist.getImageSmall();
            this.albumList = null;
            this.biosInfo = null;
            this.trackList = null;
        }
    }

    @Getter
    @ToString
    public static class Main2 {
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
        private final List<AlbumInfo.AlbumListInfo> albumList;
        private final List<TrackInfo.TrackListInfo> trackList;
        private final BiosInfo biosInfo;

        public Main2(Artist artist, BiosInfo biosInfo, List<AlbumInfo.AlbumListInfo> albumList, List<TrackInfo.TrackListInfo> trackList) {
            this.id = artist.getId();
            this.name = artist.getName();
            this.originalName = artist.getOriginalName();
            this.englishName = artist.getEnglishName();
            this.image = artist.getImage();
            this.birthday = artist.getBirthday();
            this.country = artist.getCountry();
            this.debut = artist.getDebut();
            this.agency = artist.getAgency();
            this.label = artist.getLabel();
            this.views = artist.getViews();
            this.imageSmall = artist.getImageSmall();
            this.albumList = albumList;
            this.trackList = trackList;
            this.biosInfo = biosInfo;
        }

        public Main2(Artist artist) {
            this.id = artist.getId();
            this.name = artist.getName();
            this.originalName = artist.getOriginalName();
            this.englishName = artist.getEnglishName();
            this.image = artist.getImage();
            this.birthday = artist.getBirthday();
            this.country = artist.getCountry();
            this.debut = artist.getDebut();
            this.agency = artist.getAgency();
            this.label = artist.getLabel();
            this.views = artist.getViews();
            this.imageSmall = artist.getImageSmall();
            this.albumList = null;
            this.biosInfo = null;
            this.trackList = null;
        }
    }

    /*
        아티스트 리스트 조회를 위한 클래스 입니다.
     */
    @Getter
    @ToString
    public static class ArtistListInfo {
        private final Long id;
        private final String name;
        private final String originalName;
        private final String englishName;
        private final String image;
        private final String birthday;
        private final String country;
        private final Long debut;

        public ArtistListInfo(Map<String, Object> artist) {
            this.id = Long.parseLong(String.valueOf(artist.get("id")));
            this.name = (String) artist.get("name");
            this.originalName = (String) artist.get("originalName");
            this.englishName = (String) artist.get("englishName");
            this.image = (String) artist.get("image");
            this.birthday = artist.get("birthday") != null ? String.valueOf(artist.get("birthday")) : null;
            this.country = (String) artist.get("country");
            this.debut = Long.parseLong(String.valueOf(artist.get("debut")));
        }
    }

    @Getter
    @ToString
    public static class BiosInfo {
        private final Long id;
        private final String content;

        public BiosInfo(Bios bios) {
            this.id = bios.getId();
            this.content = bios.getContent();
        }
    }

    /*
        검색 결과 아티스트 리스트 조회를 위한 클래스 입니다.
     */
    @Getter
    @ToString
    public static class SearchInfo {
        private final Long id;
        private final String name;
        private final String originalName;
        private final String englishName;
        private final String image;
        private final String birthday;
        private final String country;
        private final Long debut;

        public SearchInfo(Map<String, Object> artist) {
            this.id = Long.parseLong(String.valueOf(artist.get("id")));
            this.name = (String) artist.get("name");
            this.originalName = (String) artist.get("originalName");
            this.englishName = (String) artist.get("englishName");
            this.image = (String) artist.get("image");
            this.birthday = artist.get("birthday") != null ? String.valueOf(artist.get("birthday")) : null;
            this.country = (String) artist.get("country");
            this.debut = Long.parseLong(String.valueOf(artist.get("debut")));
        }
    }

}
