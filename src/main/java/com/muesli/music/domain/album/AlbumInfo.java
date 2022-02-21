package com.muesli.music.domain.album;

import com.muesli.music.domain.like.LikeInfo;
import lombok.Getter;
import lombok.ToString;
import com.muesli.music.domain.track.TrackInfo;

import java.util.List;


public class AlbumInfo {

    @Getter
    @ToString
    public static class Main {
        private final Long id;
        private final String albumCode;
        private final String name;
        private final String releaseDate;
        private final String originalName;
        private final String image;
        private final String description;
        private final List<TrackInfo.Main> trackList;
        private final LikeInfo.Main likeInfo;

        public Main(Album album, List<TrackInfo.Main> trackInfoList, LikeInfo.Main likeInfo) {
            this.id = album.getId();
            this.albumCode = album.getAlbumCode();
            this.name = album.getName();
            this.releaseDate = album.getReleaseDate();
            this.originalName = album.getOriginalName();
            this.image = album.getImage();
            this.description = album.getDescription();
            this.trackList = trackInfoList;
            this.likeInfo = likeInfo;
        }

        public Main(Album album, LikeInfo.Main likeInfo) {
            this.id = album.getId();
            this.albumCode = album.getAlbumCode();
            this.name = album.getName();
            this.releaseDate = album.getReleaseDate();
            this.originalName = album.getOriginalName();
            this.image = album.getImage();
            this.description = album.getDescription();
            this.trackList = null;
            this.likeInfo = likeInfo;
        }
    }

    /*
        아티스트 내 앨범 리스트 조회를 위한 클래스 입니다.
     */
    @Getter
    @ToString
    public static class AlbumBasicInfo {
        private final Long id;
        private final String albumCode;
        private final String name;
        private final String releaseDate;
        private final String originalName;
        private final String image;
        private final String description;
        private LikeInfo.Main likeInfo;

        public AlbumBasicInfo(Album album) {
            this.id = album.getId();
            this.albumCode = album.getAlbumCode();
            this.name = album.getName();
            this.releaseDate = album.getReleaseDate();
            this.originalName = album.getOriginalName();
            this.image = album.getImage();
            this.description = album.getDescription();
        }

        public void setLikeInfo(LikeInfo.Main likeInfo) {
            this.likeInfo = likeInfo;
        }

    }

}
