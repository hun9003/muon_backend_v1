package com.muesli.music.domain.track;

import java.util.List;

public interface TrackReader {
    Track getTrackBy(Long trackId);

    Track getTrackArtist(Long albumId);

    List<TrackInfo.Main> getTrackLikeList(String likeableType, Long userId);
}
