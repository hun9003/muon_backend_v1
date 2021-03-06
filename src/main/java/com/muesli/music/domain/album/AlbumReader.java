package com.muesli.music.domain.album;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface AlbumReader {

    // 앨범 정보 호출
    Album getAlbumBy(Long albumId);

    // 아티스트에 속한 앨범 리스트 개수
    int getAlbumListByArtistCount(Long artistId);

    // 아티스트에 속한 앨범 리스트 호출
    List<Map<String, Object>> getAlbumListByArtist(Long artistId, int start, int end);

   // 좋아요 된 앨범 리스트 개수
    int getAlbumLikeListCount(Long userId);

    // 좋아요 된 앨범 리스트 호출
    List<Map<String, Object>> getAlbumLikeList(Long userId, int start, int end);

    // 최신 앨범 리스트 호출
    List<Map<String, Object>> getNewAlbum(int start, int end);

    // 앨범 검색 결과 개수 호출
    int getSearchAlbumCount(String keyword);

    // 앨범 검색 결과 호출
    List<Map<String, Object>> getSearchAlbum(String keyword, String type, int start, int end);

    // 장르별 앨범 리스트 호출
    List<Map<String, Object>> getGenreAlbumList(Long genreId, Pageable pageable);

    // 채널별 앨범 리스트 개수
    int getChannelAlbumCount(Long channelId);

    // 채널별 앨범 리스트
    List<Map<String, Object>> getChannelAlbumList(Long channelId, int startNum, int endNum);

}
