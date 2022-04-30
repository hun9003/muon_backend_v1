package com.muesli.music.domain.search;

import com.muesli.music.domain.search.history.History;
import com.muesli.music.domain.search.keyword.Keyword;

import java.util.List;

public interface SearchReader {
    
    // 검색 기록 조회
    History getSearchHistory(SearchCommand.saveSearchHistory command);

    // 검색 키워드 광범위 조회
    List<Keyword> getSearchKeywordList(String keyword, String endKeyword);

    // 검색 키워드 좁은범위 조회
    List<Keyword> getSearchKeywordList(String keyword);

    // 검색 기록 리스트 조회
    List<History> getSearchHistoryList();

    // 키워드 데이터 조회
    Keyword getSearchKeyword(String keyword);

}
