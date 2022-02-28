package com.muesli.music.infrastructure.artist;

import com.muesli.music.common.exception.EntityNotFoundException;
import com.muesli.music.domain.album.AlbumInfo;
import com.muesli.music.domain.artist.Artist;
import com.muesli.music.domain.artist.ArtistInfo;
import com.muesli.music.domain.artist.ArtistReader;
import com.muesli.music.domain.like.LikeInfo;
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
        System.out.println("ArtistReaderImpl :: getArtistBy");
        return artistRepository.findById(artistId)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<AlbumInfo.AlbumBasicInfo> getAlbumList(Artist artist) {
        System.out.println("ArtistReaderImpl :: getAlbumList");
        var albumList = artist.getAlbumList();
        return albumList.stream().map(AlbumInfo.AlbumBasicInfo::new).collect(Collectors.toList());
    }

    @Override
    public List<ArtistInfo.Main> getArtistLikeList(String likeableType, Long userId) {
        System.out.println("ArtistReaderImpl :: getArtistLikeList");
        var artistList = artistRepository.findAllJoinFetch(likeableType, userId)
                .orElseThrow(EntityNotFoundException::new);
        return artistList.stream().map(
                artist -> new ArtistInfo.Main(artist, new LikeInfo.Main(artist.getLikeList().get(0)))
        ).collect(Collectors.toList());
    }
}
