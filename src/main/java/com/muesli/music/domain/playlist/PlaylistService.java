package com.muesli.music.domain.playlist;

import com.muesli.music.domain.user.UserInfo;

import java.util.List;

public interface PlaylistService {
    // 플레이리스트 조회
    PlaylistInfo.Main findPlaylistInfo(Long playlistId, String usertoken);

    // 플레이리스트 목록
    List<PlaylistInfo.Main> findPlaylistInfoMyList(String usertoken);

    // 플레이리스트 추가
    PlaylistInfo.Main registerPlaylist(PlaylistCommand.RegisterPlaylistRequest command);

    // 플레이리스트 수정
    PlaylistInfo.Main updatePlaylist(PlaylistCommand.RegisterPlaylistRequest command);

    // 플레이리스트 삭제
    void deletePlaylist(Long playlistId, String usertoken);

    // 플레이리스트 좋아요 목록
    List<PlaylistInfo.Main> getLikeList(String likeableType, String usertoken);

    // 플레이리스트에 트랙 추가

    // 플레이리스트에 트랙 삭제
}
