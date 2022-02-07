package com.muesli.music.infrastructure.album;

import com.muesli.music.common.exception.EntityNotFoundException;
import com.muesli.music.domain.album.Album;
import com.muesli.music.domain.album.AlbumInfo;
import com.muesli.music.domain.album.AlbumReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

import java.util.List;

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

    @Override
    public List<AlbumInfo.TrackInfo> getTrackList(Album album) {
        var trackList = album.getTrackList();
        return trackList.stream().map(AlbumInfo.TrackInfo::new).collect(Collectors.toList());
    }

}
