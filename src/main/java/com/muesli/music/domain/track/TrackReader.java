package com.muesli.music.domain.track;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface TrackReader {
    Track getTrackBy(Long trackId);

    int getTrackByAlbumCount(Long albumId);

    List<Map<String, Object>> getTrackListByAlbum(Long albumId, int start, int end);

    int getTrackListByArtistCount(Long artistId);

    List<Map<String, Object>> getTrackListByArtist(Long artistId, int startNum, int endNum);

    int getTrackLikeListCount(Long userId);

    List<Map<String, Object>> getTrackLikeList(Long userId, int start, int end);

    List<Map<String, Object>> getTrackRank(String begin, String end, Pageable pageable);

    List<Map<String, Object>> getTrackGenreRank(String begin, String end, Pageable pageable, Long genreId);

    List<Map<String, Object>> getNewTrack(int start, int end);

    List<Map<String, Object>> getUserHistoryTrack(Long userId, int start, int end);

    int getSearchTrackCount(String keyword);

    List<Map<String, Object>> getSearchTrack(String keyword, String type, int start, int end);

    int getGenreTrackCount(Long genreId);

    List<Map<String, Object>> getGenreTrackList(Long genreId, String type, int start, int end);

    int getPlaylistTrackCount(Long playlistId);

    List<Map<String, Object>> getPlaylistTrackList(Long playlistId, int start, int end);

}
