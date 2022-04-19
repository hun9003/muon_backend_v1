package com.muesli.music.domain.search;

import com.muesli.music.domain.search.history.History;
import com.muesli.music.domain.search.keyword.Keyword;

import java.util.List;

public interface SearchReader {
    History getSearchHistory(SearchCommand.saveSearchHistory command);

    List<Keyword> getSearchKeywordList(String keyword, String endKeyword);

    List<Keyword> getSearchKeywordList(String keyword);

    List<History> getSearchHistoryList();

    Keyword getSearchKeyword(String keyword);

}
