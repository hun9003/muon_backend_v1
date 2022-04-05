package com.muesli.music.domain.playlist;

import com.muesli.music.domain.like.LikeInfo;
import com.muesli.music.domain.track.TrackInfo;
import com.muesli.music.domain.user.UserInfo;
import lombok.Getter;
import lombok.ToString;

import java.time.ZonedDateTime;
import java.util.List;

public class PlaylistInfo {

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
        private final List<TrackInfo.Main> trackInfoList;

        public Main(Playlist playlist, UserInfo.Main userInfo, List<TrackInfo.Main> trackInfoList) {
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

}
