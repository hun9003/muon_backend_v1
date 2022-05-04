package com.muesli.music.interfaces.channel;

import com.muesli.music.common.util.Constant;
import com.muesli.music.interfaces.album.AlbumDto;
import com.muesli.music.interfaces.playlist.PlaylistDto;
import com.muesli.music.interfaces.track.TrackDto;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

public class ChannelDto {

    @Getter
    @ToString
    public static class CuratingInfoInTrack {
        private final ChannelInfo channelInfo;
        private final String type = Constant.Item.TRACK;
        private final List<TrackDto.TrackInfo> list;

        public CuratingInfoInTrack(ChannelInfo channelInfo, List<TrackDto.TrackInfo> list) {
            this.channelInfo = channelInfo;
            this.list = list;
        }
    }

    @Getter
    @ToString
    public static class CuratingInfoInAlbum {
        private final ChannelInfo channelInfo;
        private final String type = Constant.Item.ALBUM;
        private final List<AlbumDto.AlbumInfo> list;

        public CuratingInfoInAlbum(ChannelInfo channelInfo, List<AlbumDto.AlbumInfo> list) {
            this.channelInfo = channelInfo;
            this.list = list;
        }
    }

    @Getter
    @ToString
    public static class CuratingInfoInPlaylist {
        private final ChannelInfo channelInfo;
        private final String type = Constant.Item.PLAYLIST;
        private final List<PlaylistDto.ChannelPlaylistInfo> list;

        public CuratingInfoInPlaylist(ChannelInfo channelInfo, List<PlaylistDto.ChannelPlaylistInfo> list) {
            this.channelInfo = channelInfo;
            this.list = list;
        }
    }

    @Getter
    @Builder
    @ToString
    public static class ChannelInfo {
        private final Long id;
        private final String name;
        private final String slug;
        private final String contentType;
        private final String autoUpdate;
        private final String layout;
        private final String hideTitle;
        private final String userId;
        private final String seoTitle;
        private final String seoDescription;
        private final String displayName;
    }
}
