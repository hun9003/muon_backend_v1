package com.muesli.music.domain.playlist;


import com.muesli.music.domain.playlist.track.PlaylistTrack;
import com.muesli.music.domain.track.Track;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class PlaylistCommand {

    @Getter
    @Builder
    @ToString
    public static class RegisterPlaylistRequest {
        private final String name;
        private final String description;
        private final Long isPublic;
        private final Long userId;

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

    @Getter
    @Builder
    @ToString
    public static class TrackToPlaylistRequest {
        private final Playlist playlist;
        private final Track track;

        public PlaylistTrack toEntity() {
            System.out.println("PlaylistCommand :: toEntity");
            return PlaylistTrack.builder()
                    .playlist(playlist)
                    .track(track)
                    .build();
        }
    }
}
