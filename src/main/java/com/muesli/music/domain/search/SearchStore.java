package com.muesli.music.domain.search;

import com.muesli.music.domain.search.history.History;
import com.muesli.music.domain.search.keyword.Keyword;

public interface SearchStore {

    void saveSearchHistory(History history);

    void saveSearchKeyword(Keyword keyword);
}
