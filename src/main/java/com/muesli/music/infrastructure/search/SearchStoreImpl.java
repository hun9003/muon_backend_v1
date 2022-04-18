package com.muesli.music.infrastructure.search;

import com.muesli.music.domain.search.SearchStore;
import com.muesli.music.domain.search.history.History;
import com.muesli.music.domain.search.keyword.Keyword;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SearchStoreImpl implements SearchStore {
    private final HistoryRepository historyRepository;
    private final KeywordRepository keywordRepository;

    @Override
    public void saveSearchHistory(History history) {
        System.out.println("SearchStoreImpl :: saveSearchHistory");
        historyRepository.save(history);
    }

    @Override
    public void saveSearchKeyword(Keyword keyword) {
        System.out.println("SearchStoreImpl :: saveSearchKeyword");
        keywordRepository.save(keyword);
    }
}
