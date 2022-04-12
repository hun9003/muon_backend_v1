package com.muesli.music.domain.search;

import org.springframework.data.domain.Pageable;

public interface SearchService {
    // 곡 검색 결과
    SearchInfo.SearchTrack getSearchTrack(SearchCommand.SearchRequest command, Pageable pageable);

    // 앨범 검색 결과
    SearchInfo.SearchAlbum getSearchAlbum(SearchCommand.SearchRequest command, Pageable pageable);

    // 아티스트 검색 결과
    SearchInfo.SearchArtist getSearchArtist(SearchCommand.SearchRequest command, Pageable pageable);

    // 가사 검색 결과
    SearchInfo.SearchLyrics getSearchLyrics(SearchCommand.SearchRequest command, Pageable pageable);
}
