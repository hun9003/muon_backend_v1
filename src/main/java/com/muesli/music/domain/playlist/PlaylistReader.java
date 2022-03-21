package com.muesli.music.domain.playlist;

import com.muesli.music.domain.playlist.track.PlaylistTrack;
import com.muesli.music.domain.user.UserInfo;

import java.util.List;

public interface PlaylistReader {
    Playlist getPlaylistBy(Long playlistId);

    PlaylistTrack getPlaylistTrack(Long playlistId, Long TrackId);

    List<PlaylistInfo.Main> getPlaylistList(UserInfo.Main userInfo);

    List<PlaylistInfo.Main> getPlaylistLikeList(UserInfo.Main userInfo);
}
