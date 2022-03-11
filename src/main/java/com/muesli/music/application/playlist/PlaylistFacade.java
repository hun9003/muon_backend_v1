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

    public PlaylistInfo.Main registerPlaylist(PlaylistCommand.RegisterPlaylistRequest command, String usertoken) {
        System.out.println("PlaylistFacade :: registerPlaylist");
        return playlistService.registerPlaylist(command, usertoken);
    }
}
