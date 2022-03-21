package com.muesli.music.interfaces.playlist;

import com.muesli.music.domain.playlist.PlaylistCommand;
import com.muesli.music.interfaces.like.LikeDto;
import com.muesli.music.interfaces.track.TrackDto;
import com.muesli.music.interfaces.user.UserDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
        private final LikeDto.LikeInfo likeInfo;
        private final List<TrackDto.PlaylistTrackInfo> trackInfoList;
    }

    @Getter
    @Builder
    @ToString
    public static class PlaylistInfoList {
        private final List<PlaylistInfo> playlistInfoList;
    }

    @Getter
    @Builder
    @ToString
    public static class RegisterPlaylist {

        @NotBlank
        @NotEmpty(message = "대상 name(name)은 필수값입니다.")
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

        @NotNull(message = "대상 id(id)은 필수값입니다.")
        private final Long id;

        @NotBlank
        @NotEmpty(message = "대상 name(name)은 필수값입니다.")
        private final String name;

        private final Long isPublic;

        private final String description;

        private final Long userId;

        public PlaylistCommand.UpdatePlaylistRequest toCommand() {
            return PlaylistCommand.UpdatePlaylistRequest.builder()
                    .id(id)
                    .name(name)
                    .isPublic(isPublic)
                    .description(description)
                    .build();
        }
    }

    @Getter
    @Setter
    @ToString
    public static class PlaylistTracksRequest {
        @NotNull(message = "플레이리스트 ID(playlistId)은 필수값입니다.")
        private Long playlistId;
        private List<Long> trackList;
    }
}
