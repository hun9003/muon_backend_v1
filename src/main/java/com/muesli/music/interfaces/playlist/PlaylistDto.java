package com.muesli.music.interfaces.playlist;

import com.muesli.music.domain.playlist.PlaylistCommand;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class PlaylistDto {

    @Getter
    @Builder
    @ToString
    public static class PlaylistInfo {

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
