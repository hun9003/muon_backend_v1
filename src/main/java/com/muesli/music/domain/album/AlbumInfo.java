package com.muesli.music.domain.album;

import com.muesli.music.domain.artist.ArtistInfo;
import com.muesli.music.domain.track.TrackInfo;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.Map;


public class AlbumInfo {


    @Getter
    @ToString
    public static class Main {
        private final Long id;
        private final String name;
        private final String releaseDate;
        private final String originalName;
        private final String image;
        private final String description;
        private final List<TrackInfo.TrackListInfo> trackList;
        private ArtistInfo.Main artistInfo;

        public Main(Album album, List<TrackInfo.TrackListInfo> trackInfoList) {
            this.id = album.getId();
            this.name = album.getName();
            this.releaseDate = album.getReleaseDate();
            this.originalName = album.getOriginalName();
            this.image = album.getImage();
            this.description = album.getDescription();
            this.trackList = trackInfoList;
        }

        public Main(Album album) {
            this.id = album.getId();
            this.name = album.getName();
            this.releaseDate = album.getReleaseDate();
            this.originalName = album.getOriginalName();
            this.image = album.getImage();
            this.description = album.getDescription();
            this.trackList = null;
        }

        public void setArtistInfo(ArtistInfo.Main artistInfo) {
            this.artistInfo = artistInfo;
        }
    }

    /*
        아티스트 내 앨범 리스트 조회를 위한 클래스 입니다.
     */
    @Getter
    @ToString
    public static class AlbumBasicInfo {
        private final Long id;
        private final String name;
        private final String releaseDate;
        private final String originalName;
        private final String image;
        private final String description;

        public AlbumBasicInfo(Album album) {
            this.id = album.getId();
            this.name = album.getName();
            this.releaseDate = album.getReleaseDate();
            this.originalName = album.getOriginalName();
            this.image = album.getImage();
            this.description = album.getDescription();
        }
    }

    /*
        일반 앨범 리스트 정보
    */
    @Getter
    @ToString
    public static class AlbumListInfo {
        private final Long id;
        private final String name;
        private final String releaseDate;
        private final String originalName;
        private final String image;

        private final Long artistId;
        private final String artistName;

        public AlbumListInfo(Map<String, Object> album) {

            this.id = Long.parseLong(String.valueOf(album.get("id")));
            this.name = (String) album.get("name");
            this.releaseDate = (String) album.get("releaseDate");
            this.originalName = (String) album.get("originalName");
            this.image = (String) album.get("image");

            this.artistId = Long.parseLong(String.valueOf(album.get("artistId")));
            this.artistName = (String) album.get("artistName");
        }
    }

}
