package com.muesli.music.interfaces.playlist;

import com.muesli.music.common.exception.BaseException;
import com.muesli.music.common.response.ErrorCode;
import com.muesli.music.common.util.Constant;
import com.muesli.music.domain.playlist.PlaylistCommand;
import com.muesli.music.interfaces.track.TrackDto;
import com.muesli.music.interfaces.user.UserDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.ZonedDateTime;
import java.util.List;

public class PlaylistDto {

    @Getter
    @Builder
    @ToString
    public static class PlaylistInfo {
        private final Long id;
        private final String name;
        private final Long isPublic;
        private final String image;
        private final int views;
        private final String description;
        private final ZonedDateTime createAt;
        private final int trackCount;
        private final UserDto.PlaylistUserInfo userInfo;
        private final List<TrackDto.PlaylistTrackInfo> trackInfoList;
    }

    @Getter
    @ToString
    public static class PlaylistInfoList {
        private final String type;
        private final List<PlaylistInfo> list;

        public PlaylistInfoList(List<PlaylistInfo> list) {
            this.type = Constant.Item.PLAYLIST;
            this.list = list;
        }
    }

    @Getter
    @Builder
    @ToString
    public static class RegisterPlaylist {

        @NotBlank(message = "플레이리스트의 이름은 공백일 수 없습니다")
        @NotEmpty(message = "플레이리스트의 이름은 필수값입니다.")
        private final String name;

        private final Long isPublic;

        private final String description;

        private final Long userId;

        public PlaylistCommand.RegisterPlaylistRequest toCommand() {
            return PlaylistCommand.RegisterPlaylistRequest.builder()
                    .name(name)
                    .isPublic(isPublic)
                    .description(description)
                    .build();
        }
    }

    @Getter
    @Builder
    @ToString
    public static class UpdatePlaylist {

        private Long id;

        @NotBlank(message = "플레이리스트의 이름은 공백일 수 없습니다")
        @NotEmpty(message = "플레이리스트의 이름은 필수값입니다.")
        private final String name;

        private final Long isPublic;

        private final String description;

        private final Long userId;

        private List<Long> trackList;

        public void setId(Long id) {
            if(id == null) throw new BaseException(ErrorCode.COMMON_INVALID_PARAMETER);
            this.id = id;
        }

        public PlaylistCommand.UpdatePlaylistRequest toCommand() {
            return PlaylistCommand.UpdatePlaylistRequest.builder()
                    .id(id)
                    .name(name)
                    .isPublic(isPublic)
                    .description(description)
                    .trackList(trackList)
                    .build();
        }
    }

    @Getter
    @Setter
    @ToString
    public static class PlaylistTracksRequest {
        private Long playlistId;
        private List<Long> trackList;

        public void setPlaylistId(Long playlistId) {
            if(playlistId == null) throw new BaseException(ErrorCode.COMMON_INVALID_PARAMETER);
            this.playlistId = playlistId;
        }
    }

    /**
     * 채널 별 플레이리스트
     */
    @Getter
    @Builder
    @ToString
    public static class ChannelPlaylistInfo {
        private final Long id;
        private final String name;
        private final Long isPublic;
        private final String image;
        private final int views;
        private final String description;
    }
}
