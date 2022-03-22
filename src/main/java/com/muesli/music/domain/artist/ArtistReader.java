package com.muesli.music.domain.artist;

import com.muesli.music.domain.album.Album;

import java.util.List;

public interface ArtistReader {
    Artist getArtistBy(Long artistId);

    List<Album> getAlbumList(Artist artist);

    List<ArtistInfo.Main> getArtistLikeList(Long userId);
}
