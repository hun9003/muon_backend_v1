package com.muesli.music.domain.album;
import com.muesli.music.domain.track.TrackInfo;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface AlbumReader {

    // 앨범 정보 호출
    Album getAlbumBy(Long albumId);

    // 앨범에 속한 트랙 리스트 호출
   List<TrackInfo.Main> getTrackList(Album album);
    
   // 좋아요 된 앨범 리스트 호출
    List<AlbumInfo.Main> getAlbumLikeList(Long userId);

    // 최신 앨범 리스트 호출
    List<Map<String, Object>> getNewAlbum(int start, int end);

    // 앨범 검색 결과 개수 호출
    int getSearchAlbumCount(String keyword);

    // 앨범 검색 결과 호출
    List<Map<String, Object>> getSearchAlbum(String keyword, String type, int start, int end);

    // 장르별 앨범 리스트 호출
    List<Map<String, Object>> getGenreAlbumList(Long genreId, Pageable pageable);
}
