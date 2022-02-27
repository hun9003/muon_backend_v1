package com.muesli.music.domain.playlist;

import java.util.List;

public interface PlaylistReader {
    Playlist getPlaylistBy(Long playlistId);

    List<PlaylistInfo.Main> getPlaylistList(Long userId);

    List<PlaylistInfo.Main> getPlaylistLikeList(String likeableType, Long userId);
}
