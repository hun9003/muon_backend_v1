package com.muesli.music.infrastructure.playlist;

import com.muesli.music.common.exception.InvalidParamException;
import com.muesli.music.domain.playlist.Playlist;
import com.muesli.music.domain.playlist.PlaylistStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PlaylistStoreImpl implements PlaylistStore {
    private final PlaylistRepository playlistRepository;

    @Override
    public Playlist store(Playlist initPlaylist) {
        System.out.println("PlaylistStoreImpl :: store");
        if (initPlaylist.getUserId() == null) throw new InvalidParamException("Playlist.UserId");
        return playlistRepository.save(initPlaylist);
    }

    @Override
    public void delete(Playlist playlist) {
        System.out.println("PlaylistStoreImpl :: delete");
        if (playlist.getId() == null) throw new InvalidParamException("Playlist.id");
        playlistRepository.delete(playlist);
    }
}
