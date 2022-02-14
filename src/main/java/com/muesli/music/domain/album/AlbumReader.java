package com.muesli.music.domain.album;

import java.util.List;

public interface AlbumReader {
    Album getAlbumBy(Long albumId);

    List<AlbumInfo.TrackInfo> getTrackList(Album album);

}
