package com.muesli.music.domain.album;
import com.muesli.music.domain.track.TrackInfo;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface AlbumReader {

    // 앨범 PK로 앨범 정보 호출
    Album getAlbumBy(Long albumId);

    // 앨범 PK로 속한 트랙 리스트 호출
   List<TrackInfo.Main> getTrackList(Album album);
    
   // 유저 PK로 좋아요 된 앨범 리스트 호출
    List<AlbumInfo.Main> getAlbumLikeList(Long userId);

    List<Map<String, Object>> getNewAlbum(int start, int end);

    int getSearchAlbumCount(String keyword);

    List<Map<String, Object>> getSearchAlbum(String keyword, String type, int start, int end);

    List<Map<String, Object>> getGenreAlbumList(Long genreId, Pageable pageable);
}
