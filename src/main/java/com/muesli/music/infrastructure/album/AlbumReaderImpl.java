package com.muesli.music.infrastructure.album;

import com.muesli.music.common.exception.EntityNotFoundException;
import com.muesli.music.domain.album.Album;
import com.muesli.music.domain.album.AlbumInfo;
import com.muesli.music.domain.album.AlbumReader;
import com.muesli.music.domain.artist.ArtistInfo;
import com.muesli.music.domain.like.Like;
import com.muesli.music.domain.like.LikeInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import com.muesli.music.domain.track.TrackInfo;
import java.util.stream.Collectors;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class AlbumReaderImpl implements AlbumReader {
    private final AlbumRepository albumRepository;

    @Override
    public Album getAlbumBy(Long albumId) {
        return albumRepository.findAlbumById(albumId)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<TrackInfo.Main> getTrackList(Album album) {
        var trackList = album.getTrackList();
        return trackList.stream().map(track -> {
            var artistInfo = new ArtistInfo.Main(track.getTrackArtists().iterator().next().getArtist());
            return new TrackInfo.Main(track, artistInfo);
        }).collect(Collectors.toList());
    }

    @Override
    public List<AlbumInfo.Main> getAlbumLikeList(Long userId) {
        System.out.println("AlbumReaderImpl :: getAlbumLikeList");
        var albumList = albumRepository.findAllLikeList(userId)
                .orElseThrow(EntityNotFoundException::new);
        return albumList.stream().map(AlbumInfo.Main::new).collect(Collectors.toList());
    }
}
