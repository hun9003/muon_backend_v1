package com.muesli.music.application.playlist;

import com.muesli.music.domain.playlist.PlaylistCommand;
import com.muesli.music.domain.playlist.PlaylistInfo;
import com.muesli.music.domain.playlist.PlaylistService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlaylistFacade {
    private final PlaylistService playlistService;

    /**
     * 플레이 리스트 생성
     *
     * @param command
     * @param token
     * @return
     */
    public PlaylistInfo.Main registerPlaylist(PlaylistCommand.RegisterPlaylistRequest command, String token) {
        System.out.println("PlaylistFacade :: registerPlaylist");
        return playlistService.registerPlaylist(command, token);
    }

    /**
     * 플레이 리스트 수정
     *
     * @param command
     * @param token
     * @return
     */
    public void updatePlaylist(PlaylistCommand.UpdatePlaylistRequest command, String token) {
        System.out.println("PlaylistFacade :: updatePlaylist");
        playlistService.updatePlaylist(command, token);
    }

    /**
     * 플레이 리스트 삭제
     * @param playlistId
     * @param token
     */
    public void removePlaylist(Long playlistId, String token) {
        System.out.println("PlaylistFacade :: removePlaylist");
        playlistService.removePlaylist(playlistId, token);
    }
}
