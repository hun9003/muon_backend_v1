package com.muesli.music.infrastructure.album;

import com.muesli.music.common.exception.EntityNotFoundException;
import com.muesli.music.domain.album.Album;
import com.muesli.music.domain.album.AlbumReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AlbumReaderImpl implements AlbumReader {
    private final AlbumRepository albumRepository;

    @Override
    public Album getAlbumBy(Long albumId) {
        return albumRepository.findById(albumId)
                .orElseThrow(EntityNotFoundException::new);
    }
}
