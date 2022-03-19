package com.muesli.music.domain.playlist;

import com.muesli.music.domain.playlist.track.PlaylistTrack;

import java.util.List;

public interface PlaylistReader {
    Playlist getPlaylistBy(Long playlistId);

    PlaylistTrack getPlaylistTrack(Long playlistId, Long TrackId);

    List<PlaylistInfo.Main> getPlaylistList(Long userId);

    List<PlaylistInfo.Main> getPlaylistLikeList(String likeableType, Long userId);
}
