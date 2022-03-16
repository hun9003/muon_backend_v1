package com.muesli.music.domain.playlist;

import java.util.List;

public interface PlaylistService {
    // 플레이리스트 조회
    PlaylistInfo.Main findPlaylistInfo(Long playlistId, String token);

    // 플레이리스트 목록
    List<PlaylistInfo.Main> findPlaylistInfoMyList(String token);

    // 플레이리스트 추가
    PlaylistInfo.Main registerPlaylist(PlaylistCommand.RegisterPlaylistRequest command, String token);

    // 플레이리스트 수정
    void updatePlaylist(PlaylistCommand.UpdatePlaylistRequest command, String token);

    // 플레이리스트 삭제
    void removePlaylist(Long playlistId, String token);

    // 플레이리스트 좋아요 목록
    List<PlaylistInfo.Main> getLikeList(String likeableType, String token);

    // 플레이리스트에 트랙 추가
    void addTrackToPlaylist(PlaylistCommand.TrackToPlaylistRequest command, String token);

    // 플레이리스트에 트랙 삭제
    void removeTrackToPlaylist(PlaylistCommand.TrackToPlaylistRequest command, String token);
}
