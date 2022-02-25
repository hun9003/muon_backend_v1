package com.muesli.music.domain.playlist;


import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class PlaylistCommand {

    @Getter
    @Builder
    @ToString
    public static class RegisterPlaylistRequest {
        private String name;
        private String description;
        private Long isPublic;
        private Long userId;

        public Playlist toEntity(Long userId) {
            System.out.println("PlaylistCommand :: toEntity");
            return Playlist.builder()
                    .userId(userId)
                    .name(name)
                    .description(description)
                    .isPublic(isPublic)
                    .build();
        }
    }
}
