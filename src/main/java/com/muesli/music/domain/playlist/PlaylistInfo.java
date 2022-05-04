package com.muesli.music.domain.playlist;

import com.muesli.music.domain.track.TrackInfo;
import com.muesli.music.domain.user.UserInfo;
import lombok.Getter;
import lombok.ToString;

import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

public class PlaylistInfo {

//    @Getter
//    @ToString
//    public static class Main {
//        private final Long id;
//        private final String name;
//        private final Long isPublic;
//        private final String image;
//        private final int views;
//        private final String description;
//        private final ZonedDateTime createAt;
//        private int trackCount;
//        private final UserInfo.Main userInfo;
//        private final List<TrackInfo.Main> trackInfoList;
//
//        public Main(Playlist playlist, UserInfo.Main userInfo, List<TrackInfo.Main> trackInfoList) {
//            this.id = playlist.getId();
//            this.name = playlist.getName();
//            this.isPublic = playlist.getIsPublic();
//            this.image = playlist.getImage();
//            this.views = playlist.getViews();
//            this.description = playlist.getDescription();
//            this.userInfo = userInfo;
//            this.createAt = playlist.getCreatedAt();
//            this.trackInfoList = trackInfoList;
//        }
//
//        public Main(Playlist playlist, UserInfo.Main userInfo) {
//            this.id = playlist.getId();
//            this.name = playlist.getName();
//            this.isPublic = playlist.getIsPublic();
//            this.image = playlist.getImage();
//            this.views = playlist.getViews();
//            this.description = playlist.getDescription();
//            this.userInfo = userInfo;
//            this.createAt = playlist.getCreatedAt();
//            this.trackInfoList = null;
//        }
//
//        public void setTrackCount(int trackCount) {
//            this.trackCount = trackCount;
//        }
//    }

    @Getter
    @ToString
    public static class Main {
        private final Long id;
        private final String name;
        private final Long isPublic;
        private final String image;
        private final int views;
        private final String description;
        private final ZonedDateTime createAt;
        private int trackCount;
        private final UserInfo.Main userInfo;
        private final List<TrackInfo.TrackListInfo> trackInfoList;

        public Main(Playlist playlist, UserInfo.Main userInfo, List<TrackInfo.TrackListInfo> trackInfoList) {
            this.id = playlist.getId();
            this.name = playlist.getName();
            this.isPublic = playlist.getIsPublic();
            this.image = playlist.getImage();
            this.views = playlist.getViews();
            this.description = playlist.getDescription();
            this.userInfo = userInfo;
            this.createAt = playlist.getCreatedAt();
            this.trackInfoList = trackInfoList;
        }

        public Main(Playlist playlist, UserInfo.Main userInfo) {
            this.id = playlist.getId();
            this.name = playlist.getName();
            this.isPublic = playlist.getIsPublic();
            this.image = playlist.getImage();
            this.views = playlist.getViews();
            this.description = playlist.getDescription();
            this.userInfo = userInfo;
            this.createAt = playlist.getCreatedAt();
            this.trackInfoList = null;
        }

        public void setTrackCount(int trackCount) {
            this.trackCount = trackCount;
        }
    }

    @Getter
    @ToString
    public static class PlayListInfo {
        private final Long id;
        private final String name;
        private final String image;
        private final Long isPublic;
        private final int views;
        private final String description;
        private final Timestamp createAt;
        private final int trackCount;
        private final Long userId;
        private final String userName;

        public PlayListInfo(Map<String, Object> playlist) {
            this.id = Long.parseLong(String.valueOf(playlist.get("id")));
            this.name = (String) playlist.get("name");
            this.image = (String) playlist.get("image");
            this.views = Integer.parseInt(String.valueOf(playlist.get("views")));
            this.isPublic = (Boolean)playlist.get("isPublic") ? 1L : 0L;
            this.description = (String) playlist.get("description");
            this.userId = Long.parseLong(String.valueOf(playlist.get("userId")));
            this.userName = (String) playlist.get("userName");
            this.createAt = (Timestamp) playlist.get("createAt");
            this.trackCount = Integer.parseInt(String.valueOf(playlist.get("trackCount")));
        }

    }

}
