package com.muesli.music.domain.track;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface TrackReader {
    Track getTrackBy(Long trackId);

    Track getTrackArtist(Long albumId);

    List<TrackInfo.Main> getTrackLikeList(Long userId);

    List<Map<String, Object>> getTrackRank(String begin, String end, Pageable pageable);

    List<Map<String, Object>> getTrackGenreRank(String begin, String end, Pageable pageable, Long genreId);
}
