package com.muesli.music.domain.playlist;

import com.muesli.music.domain.playlist.track.PlaylistTrack;

public interface PlaylistStore {
    Playlist store(Playlist initPlaylist);

    PlaylistTrack store(PlaylistTrack initPlaylistTrack);

    void delete(Playlist playlist);

    void delete(PlaylistTrack playlistTrack);

    void deletePlaylistTrack(Long playlistId);

    void updatePosition(PlaylistTrack playlistTrack);

}
