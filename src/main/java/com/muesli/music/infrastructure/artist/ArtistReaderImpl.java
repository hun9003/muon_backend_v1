package com.muesli.music.infrastructure.artist;

import com.muesli.music.common.exception.EntityNotFoundException;
import com.muesli.music.common.util.Constant;
import com.muesli.music.domain.album.Album;
import com.muesli.music.domain.artist.Artist;
import com.muesli.music.domain.artist.ArtistInfo;
import com.muesli.music.domain.artist.ArtistReader;
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
public class ArtistReaderImpl implements ArtistReader {
    private final ArtistRepository artistRepository;

    @Override
    public Artist getArtistBy(Long artistId) {
        System.out.println("ArtistReaderImpl :: getArtistBy");
        return artistRepository.findArtistById(artistId)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<Album> getAlbumList(Artist artist) {
        System.out.println("ArtistReaderImpl :: getAlbumList");
        return artist.getAlbumList();
    }

    @Override
    public List<ArtistInfo.Main> getArtistLikeList(Long userId) {
        System.out.println("ArtistReaderImpl :: getArtistLikeList");
        var artistList = artistRepository.findAllLikeList(userId)
                .orElseThrow(EntityNotFoundException::new);
        return artistList.stream().map(ArtistInfo.Main::new).collect(Collectors.toList());
    }

    @Override
    public int getSearchArtistCount(String keyword) {
        System.out.println("ArtistReaderImpl :: getSearchArtistCount");
        return artistRepository.findSearchArtistCount(keyword).orElse(0);
    }

    @Override
    public List<Map<String, Object>> getSearchArtist(String keyword, String type, int start, int end) {
        System.out.println("ArtistReaderImpl :: getSearchArtist");
        List<Map<String, Object>> artistList;
        switch (type) {
            case Constant.Order.ALPHA: artistList = artistRepository.findSearchArtistOrderByAlpha(keyword, start, end).orElse(new ArrayList<>()); break;
            case Constant.Order.POPULARITY:
            default: artistList = artistRepository.findSearchArtistOrderByPopularity(keyword, start, end).orElse(new ArrayList<>());
        }
        return artistList;
    }
}
