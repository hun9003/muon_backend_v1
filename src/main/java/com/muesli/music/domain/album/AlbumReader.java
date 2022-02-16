package com.muesli.music.domain.album;
import com.muesli.music.domain.track.TrackInfo;
import java.util.List;

public interface AlbumReader {
    Album getAlbumBy(Long albumId);

    List<TrackInfo.Main> getTrackList(Album album);

}
