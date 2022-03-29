package com.muesli.music.domain.track;

import java.util.List;

public interface TrackReader {
    Track getTrackBy(Long trackId);

    Track getTrackArtist(Long albumId);

    List<TrackInfo.Main> getTrackLikeList(Long userId);

    List<Track> getTop100List();
}
