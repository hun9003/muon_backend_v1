package com.muesli.music.domain.artist;

import com.muesli.music.domain.album.Album;

import java.util.List;
import java.util.Map;

public interface ArtistReader {
    
    // 아티스트 PK로 아티스트 정보 호출
    Artist getArtistBy(Long artistId);

    List<Album> getAlbumList(Artist artist);

    List<ArtistInfo.Main> getArtistLikeList(Long userId);

    int getSearchArtistCount(String keyword);

    List<Map<String, Object>> getSearchArtist(String keyword, String type, int start, int end);
}
