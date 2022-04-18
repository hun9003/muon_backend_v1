package com.muesli.music.infrastructure.search;

import com.google.common.collect.Lists;
import com.muesli.music.domain.search.SearchCommand;
import com.muesli.music.domain.search.SearchReader;
import com.muesli.music.domain.search.history.History;
import com.muesli.music.domain.search.keyword.Keyword;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class SearchReaderImpl implements SearchReader {
    private final HistoryRepository historyRepository;
    private final KeywordRepository keywordRepository;

    @Override
    public History getSearchHistory(SearchCommand.saveSearchHistory command) {
        System.out.println("SearchReaderImpl :: getsearchHistory");
        return historyRepository.findHistoryByKeyword(command.getKeyword(), command.getUserId(), command.getIp()).orElse(new History());
    }

    @Override
    public List<Keyword> getSearchKeywordList(String keyword) {
        System.out.println("SearchReaderImpl :: getSearchKeywordList");
        return null;
    }

    @Override
    public List<History> getSearchHistoryList() {
        System.out.println("SearchReaderImpl :: getSearchHistoryList");
        return historyRepository.findHistoryAll().orElse(Lists.newArrayList());
    }

    @Override
    public Keyword getSearchKeyword(String keyword) {
        System.out.println("SearchReaderImpl :: getSearchKeyword");
        return keywordRepository.findKeywordBykeyword(keyword).orElse(new Keyword());
    }
}
