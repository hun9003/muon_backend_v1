package com.muesli.music.domain.album;
import com.muesli.music.domain.track.TrackInfo;
import java.util.List;
import java.util.Map;

public interface AlbumReader {
    Album getAlbumBy(Long albumId);

    List<TrackInfo.Main> getTrackList(Album album);

    List<AlbumInfo.Main> getAlbumLikeList(Long userId);

    List<Map<String, Object>> getNewAlbum(int start, int end);

    int getSearchAlbumCount(String keyword);

    List<Map<String, Object>> getSearchAlbum(String keyword, String type, int start, int end);
}
