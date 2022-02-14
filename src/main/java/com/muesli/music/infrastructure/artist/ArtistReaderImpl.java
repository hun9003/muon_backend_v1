package com.muesli.music.infrastructure.artist;

import com.muesli.music.common.exception.EntityNotFoundException;
import com.muesli.music.domain.album.AlbumInfo;
import com.muesli.music.domain.artist.Artist;
import com.muesli.music.domain.artist.ArtistReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class ArtistReaderImpl implements ArtistReader {
    private final ArtistRepository artistRepository;

    @Override
    public Artist getArtistBy(Long artistId) {
        return artistRepository.findById(artistId)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<AlbumInfo.AlbumBasicInfo> getAlbumList(Artist artist) {
        var albumList = artist.getAlbumList();
        return albumList.stream().map(AlbumInfo.AlbumBasicInfo::new).collect(Collectors.toList());
    }
}
