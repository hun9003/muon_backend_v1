package com.muesli.music.domain.artist;

import com.muesli.music.domain.album.AlbumInfo;
import com.muesli.music.domain.artist.Artist;

import java.util.List;

public interface ArtistReader {
    Artist getArtistBy(Long artistId);

    List<AlbumInfo.AlbumBasicInfo> getAlbumList(Artist artist);
}
