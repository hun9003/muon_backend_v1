package com.muesli.music.domain.playlist;


import com.muesli.music.domain.playlist.track.PlaylistTrack;
import com.muesli.music.domain.track.Track;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

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
    public static class UpdatePlaylistRequest {
        private final Long id;
        private final String name;
        private final String description;
        private final Long isPublic;
        private List<Long> trackList;

        public Playlist toEntity(Long userId) {
            System.out.println("PlaylistCommand :: toEntity");
            return Playlist.builder()
                    .id(id)
                    .userId(userId)
                    .name(name)
                    .description(description)
                    .isPublic(isPublic)
                    .build();
        }

        public TrackToPlaylistRequest playlistTrackToCommand(Long playlistId, List<Long> trackList) {
            return TrackToPlaylistRequest.builder()
                    .playlistId(playlistId)
                    .trackList(trackList)
                    .build();
        }
    }

    @Getter
    @Builder
    @ToString
    public static class TrackToPlaylistRequest {
        private final Long playlistId;
        private List<Long> trackList;

        public PlaylistTrack toEntity(Playlist playlist, Track track, int position) {
            System.out.println("PlaylistCommand :: toEntity");
            return PlaylistTrack.builder()
                    .playlist(playlist)
                    .track(track)
                    .position(position)
                    .build();
        }

        public void setTrackList(List<Long> trackList) {
            this.trackList = trackList;
        }
    }
}
