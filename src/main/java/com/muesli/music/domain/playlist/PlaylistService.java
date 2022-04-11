package com.muesli.music.domain.playlist;

import com.muesli.music.domain.playlist.track.PlaylistTrack;
import com.muesli.music.domain.user.UserInfo;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PlaylistService {
    // 플레이리스트 조회
    PlaylistInfo.Main findPlaylistInfo(Long playlistId, UserInfo.Main userInfo, Pageable pageable);

    // 플레이리스트 목록
    List<PlaylistInfo.Main> findPlaylistInfoMyList(UserInfo.Main userInfo, Pageable pageable);

    // 플레이리스트 추가
    PlaylistInfo.Main registerPlaylist(PlaylistCommand.RegisterPlaylistRequest command, UserInfo.Main userInfo);

    // 플레이리스트 수정
    void updatePlaylist(PlaylistCommand.UpdatePlaylistRequest command, UserInfo.Main userInfo);

    // 플레이리스트 삭제
    void removePlaylist(Long playlistId, UserInfo.Main userInfo);

    // 플레이리스트 좋아요 목록
    List<PlaylistInfo.Main> getLikeList(String token, Pageable pageable);

    // 플레이리스트에 트랙 추가
    void addTrackToPlaylist(PlaylistCommand.TrackToPlaylistRequest command, UserInfo.Main userInfo);

    // 플레이리스트에 트랙 삭제
    void removeTrackToPlaylist(PlaylistCommand.TrackToPlaylistRequest command, UserInfo.Main userInfo);

    // 플레이리스트의 트랙 조회
    List<PlaylistTrack> getTrackToPlaylist(Long playlistId, UserInfo.Main userInfo);
}
