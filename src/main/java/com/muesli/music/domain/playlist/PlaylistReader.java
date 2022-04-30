package com.muesli.music.domain.playlist;

import com.muesli.music.domain.playlist.track.PlaylistTrack;
import com.muesli.music.domain.user.UserInfo;

import java.util.List;

public interface PlaylistReader {
    
    // 플레이리스트 데이터 호출
    Playlist getPlaylistBy(Long playlistId);

    // 플레이리스트 트랙 데이터 리스트 호출
    PlaylistTrack getPlaylistTrack(Long playlistId, Long TrackId);

    // 플레이리스트 목록 호출
    List<PlaylistInfo.Main> getPlaylistList(UserInfo.Main userInfo);

    // 좋아하는 플레이리스트 목록 호출
    List<PlaylistInfo.Main> getPlaylistLikeList(UserInfo.Main userInfo);
}
