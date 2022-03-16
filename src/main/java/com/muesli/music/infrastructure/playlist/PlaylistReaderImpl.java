package com.muesli.music.infrastructure.playlist;

import com.muesli.music.common.exception.EntityNotFoundException;
import com.muesli.music.domain.playlist.Playlist;
import com.muesli.music.domain.playlist.PlaylistInfo;
import com.muesli.music.domain.playlist.PlaylistReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class PlaylistReaderImpl implements PlaylistReader {
    private final PlaylistRepository playlistRepository;

    @Override
    public Playlist getPlaylistBy(Long playlistId) {
        System.out.println("PlaylistReaderImpl :: getPlaylistBy");
        return playlistRepository.findPlaylistById(playlistId)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<PlaylistInfo.Main> getPlaylistList(Long userId) {
        System.out.println("PlaylistReaderImpl :: getPlaylistList");
        return null;
    }

    @Override
    public List<PlaylistInfo.Main> getPlaylistLikeList(String likeableType, Long userId) {
        System.out.println("PlaylistReaderImpl :: getPlaylistLikeList");
        return null;
    }
}
