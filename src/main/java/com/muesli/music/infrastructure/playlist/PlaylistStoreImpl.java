package com.muesli.music.infrastructure.playlist;

import com.muesli.music.common.exception.InvalidParamException;
import com.muesli.music.domain.playlist.Playlist;
import com.muesli.music.domain.playlist.PlaylistStore;
import com.muesli.music.domain.playlist.track.PlaylistTrack;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PlaylistStoreImpl implements PlaylistStore {
    private final PlaylistRepository playlistRepository;
    private final PlaylistTrackRepository playlistTrackRepository;

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

    @Override
    public PlaylistTrack store(PlaylistTrack initPlaylistTrack) {
        System.out.println("PlaylistStoreImpl :: store");
        if (initPlaylistTrack.getPlaylist() == null) throw new InvalidParamException("PlaylistTrack.Playlist");
        if (initPlaylistTrack.getTrack() == null) throw new InvalidParamException("PlaylistTrack.Track");
        return playlistTrackRepository.save(initPlaylistTrack);
    }

    @Override
    public void delete(PlaylistTrack playlistTrack) {
        System.out.println("PlaylistStoreImpl :: delete");
        if (playlistTrack.getId() == null) throw new InvalidParamException("playlistTrack.id");
        playlistTrackRepository.delete(playlistTrack);

    }

    @Override
    public void deletePlaylistTrack(Long playlistId) {
        System.out.println("PlaylistStoreImpl :: deletePlaylistTrack");
        if (playlistId == null) throw new InvalidParamException("playlistTrack.id");
        playlistTrackRepository.deleteByPlaylistId(playlistId);
    }

    @Override
    public void updatePosition(PlaylistTrack playlistTrack) {
        System.out.println("PlaylistStoreImpl :: updatePosition");
        playlistTrackRepository.changePosition(playlistTrack.getPlaylist().getId(), playlistTrack.getPosition());
    }
}
