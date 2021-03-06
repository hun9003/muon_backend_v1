package com.muesli.music.domain.playlist;

import com.muesli.music.domain.playlist.track.PlaylistTrack;

import java.util.List;
import java.util.Map;

public interface PlaylistReader {
    
    // 플레이리스트 데이터 호출
    Playlist getPlaylistBy(Long playlistId);
    Playlist getPlaylistBy2(Long playlistId);

    // 플레이리스트 트랙 데이터 리스트 호출
    PlaylistTrack getPlaylistTrack(Long playlistId, Long TrackId);

    // 플레이리스트 목록 개수
    int getPlaylistListCount(Long userId);

    // 플레이리스트 목록 호출
    List<Map<String, Object>> getPlaylistList(Long userId, int start, int end);
    
    // 좋아하는 플레이리스트 목록 개수
    int getPlaylistLikeListCount(Long userId);

    // 좋아하는 플레이리스트 목록 호출
    List<Map<String, Object>> getPlaylistLikeList(Long userId, int start, int end);
}
