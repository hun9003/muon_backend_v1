package com.muesli.music.domain.artist;

import com.muesli.music.domain.album.Album;

import java.util.List;
import java.util.Map;

public interface ArtistReader {
    
    // 아티스트 정보 호출
    Artist getArtistBy(Long artistId);

    // 아티스트에 소속된 앨범 리스트 호출
    List<Album> getAlbumList(Artist artist);

    // 좋아하는 아티스트 리스트 호출
    List<ArtistInfo.Main> getArtistLikeList(Long userId);

    // 아티스트 검색 결과 개수 호출
    int getSearchArtistCount(String keyword);

    // 아티스트 검색 결과 호출
    List<Map<String, Object>> getSearchArtist(String keyword, String type, int start, int end);
}
