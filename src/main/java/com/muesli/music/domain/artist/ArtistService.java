package com.muesli.music.domain.artist;

import com.muesli.music.domain.search.SearchCommand;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ArtistService {
    
    // 아티스트 정보 호출
    ArtistInfo.Main findArtistInfo(Long artistId, Pageable pageable);

    // 좋아하는 아티스트 리스트 호출
    List<ArtistInfo.Main> getLikeList(String token, Pageable pageable);

    // 아티스트 검색 결과 호출
    List<ArtistInfo.SearchInfo> getSearchArtist(SearchCommand.SearchRequest command, Pageable pageable);

    // 아티스트 검색 결과 개수 호출
    int getSearchArtistCount(SearchCommand.SearchRequest command);
}
