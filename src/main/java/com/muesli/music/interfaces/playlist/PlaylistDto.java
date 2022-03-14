package com.muesli.music.interfaces.playlist;

import com.muesli.music.domain.like.LikeInfo;
import com.muesli.music.domain.playlist.PlaylistCommand;
import com.muesli.music.domain.track.TrackInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
        private final LikeInfo.Main likeInfo;
        private final List<TrackInfo.Main> trackInfoList;
    }

    @Getter
    @Builder
    @ToString
    public static class PlaylistTrackInfo {

    }

    @Getter
    @Builder
    @ToString
    public static class PlaylistTrackList {

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
}
