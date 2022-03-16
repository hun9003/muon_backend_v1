package com.muesli.music.application.playlist;

import com.muesli.music.domain.playlist.PlaylistCommand;
import com.muesli.music.domain.playlist.PlaylistInfo;
import com.muesli.music.domain.playlist.PlaylistService;
import com.muesli.music.domain.user.token.UsertokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlaylistFacade {
    private final PlaylistService playlistService;
    private final UsertokenService usertokenService;

    public PlaylistInfo.Main findPlaylistInfo(Long playlistId, String token, Pageable pageable) {
        System.out.println("PlaylistFacade :: registerPlaylist");
        var usertokenInfo = usertokenService.findUsertokenInfo(token);
        return playlistService.findPlaylistInfo(playlistId, usertokenInfo.getUserInfo(), pageable);
    }

    /**
     * 플레이 리스트 생성
     *
     * @param command
     * @param token
     * @return
     */
    public PlaylistInfo.Main registerPlaylist(PlaylistCommand.RegisterPlaylistRequest command, String token) {
        System.out.println("PlaylistFacade :: registerPlaylist");
        var usertokenInfo = usertokenService.findUsertokenInfo(token);
        return playlistService.registerPlaylist(command, usertokenInfo.getUserInfo());
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
        var usertokenInfo = usertokenService.findUsertokenInfo(token);
        playlistService.updatePlaylist(command, usertokenInfo.getUserInfo());
    }

    /**
     * 플레이 리스트 삭제
     * @param playlistId
     * @param token
     */
    public void removePlaylist(Long playlistId, String token) {
        System.out.println("PlaylistFacade :: removePlaylist");
        var usertokenInfo = usertokenService.findUsertokenInfo(token);
        playlistService.removePlaylist(playlistId, usertokenInfo.getUserInfo());
    }

}
