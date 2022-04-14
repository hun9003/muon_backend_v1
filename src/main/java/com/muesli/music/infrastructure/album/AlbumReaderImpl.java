package com.muesli.music.infrastructure.album;

import com.muesli.music.common.exception.EntityNotFoundException;
import com.muesli.music.common.exception.InvalidParamException;
import com.muesli.music.domain.album.Album;
import com.muesli.music.domain.album.AlbumInfo;
import com.muesli.music.domain.album.AlbumReader;
import com.muesli.music.domain.artist.ArtistInfo;
import com.muesli.music.domain.track.TrackInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Override
    public List<Map<String, Object>> getNewAlbum(int start, int end) {
        System.out.println("AlbumReaderImpl :: getNewAlbum");
        return albumRepository.findNewAlbum(start, end).orElseThrow(InvalidParamException::new);
    }

    @Override
    public int getSearchAlbumCount(String keyword) {
        System.out.println("AlbumReaderImpl :: getSearchAlbumCount");
        return albumRepository.findSearchAlbumCount(keyword).orElse(0);
    }

    @Override
    public List<Map<String, Object>> getSearchAlbum(String keyword, String type, int start, int end) {
        System.out.println("AlbumReaderImpl :: getSearchAlbum");
        List<Map<String, Object>> albumList;
        switch (type) {
            case "similar" : albumList = albumRepository.findSearchAlbumOrderBySimilar(keyword, start, end).orElse(new ArrayList<>()); break;
            case "newest" : albumList = albumRepository.findSearchAlbumOrderByNewest(keyword, start, end).orElse(new ArrayList<>()); break;
            case "popularity":
            default: albumList = albumRepository.findSearchAlbumOrderByPopularity(keyword, start, end).orElse(new ArrayList<>());
        }
        return albumList;
    }
}
