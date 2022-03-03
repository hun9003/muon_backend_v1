package com.muesli.music.infrastructure.artist;

import com.muesli.music.common.exception.EntityNotFoundException;
import com.muesli.music.domain.album.AlbumInfo;
import com.muesli.music.domain.artist.Artist;
import com.muesli.music.domain.artist.ArtistInfo;
import com.muesli.music.domain.artist.ArtistReader;
import com.muesli.music.domain.like.Like;
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
        return artistRepository.findArtistById(artistId)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<AlbumInfo.AlbumBasicInfo> getAlbumList(Artist artist) {
        System.out.println("ArtistReaderImpl :: getAlbumList");
        var albumList = artist.getAlbumList();
        return albumList.stream().map(album -> {
            var likeInfo = new LikeInfo.Main(new Like());
            likeInfo.setLikeCount((long) album.getLikeList().size());
            var albumInfo = new AlbumInfo.AlbumBasicInfo(album);
            albumInfo.setLikeInfo(likeInfo);
            return albumInfo;
        }).collect(Collectors.toList());
    }

    @Override
    public List<ArtistInfo.Main> getArtistLikeList(Long userId) {
        System.out.println("ArtistReaderImpl :: getArtistLikeList");
        var artistList = artistRepository.findLikeArtist(userId)
                .orElseThrow(EntityNotFoundException::new);
        return artistList.stream().map(
                artist -> new ArtistInfo.Main(artist, new LikeInfo.Main(artist.getLikeList().iterator().next()))
        ).collect(Collectors.toList());
    }
}
