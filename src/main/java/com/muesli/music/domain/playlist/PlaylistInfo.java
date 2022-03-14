package com.muesli.music.domain.playlist;

import com.muesli.music.domain.like.LikeInfo;
import com.muesli.music.domain.track.TrackInfo;
import lombok.Getter;
import lombok.ToString;

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
        private final Long userId;
        private final LikeInfo.Main likeInfo;
        private final List<TrackInfo.Main> trackInfoList;

        public Main(Playlist playlist, List<TrackInfo.Main> trackInfoList, LikeInfo.Main likeInfo) {
            this.id = playlist.getId();
            this.name = playlist.getName();
            this.isPublic = playlist.getIsPublic();
            this.image = playlist.getImage();
            this.views = playlist.getViews();
            this.description = playlist.getDescription();
            this.userId = playlist.getUserId();
            this.trackInfoList = trackInfoList;
            this.likeInfo = likeInfo;
        }

        public Main(Playlist playlist) {
            this.id = playlist.getId();
            this.name = playlist.getName();
            this.isPublic = playlist.getIsPublic();
            this.image = playlist.getImage();
            this.views = playlist.getViews();
            this.description = playlist.getDescription();
            this.userId = playlist.getUserId();
            this.trackInfoList = null;
            this.likeInfo = null;
        }
    }

}
