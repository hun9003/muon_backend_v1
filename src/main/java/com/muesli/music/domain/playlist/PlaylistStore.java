package com.muesli.music.domain.playlist;

import com.muesli.music.domain.playlist.track.PlaylistTrack;

public interface PlaylistStore {
    
    // 플레이리스트 저장
    Playlist store(Playlist initPlaylist);

    // 플레이리스트 트랙 저장
    PlaylistTrack store(PlaylistTrack initPlaylistTrack);

    // 플레이리스트 삭제
    void delete(Playlist playlist);

    // 플레이리스트 트랙 삭제
    void delete(PlaylistTrack playlistTrack);

    // 플레이리스트 트랙 리스트 삭제
    void deletePlaylistTrack(Long playlistId);

    // 플레이리스트 트랙 순서 변경
    void updatePosition(PlaylistTrack playlistTrack);

}
