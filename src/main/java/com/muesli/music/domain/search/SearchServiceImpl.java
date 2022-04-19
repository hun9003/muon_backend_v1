package com.muesli.music.domain.search;

import com.muesli.music.common.util.KeywordScanner;
import com.muesli.music.domain.search.keyword.Keyword;
import com.muesli.music.domain.search.keyword.KeywordInfo;
import com.muesli.music.domain.user.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {
    private final SearchReader searchReader;
    private final SearchStore searchStore;

    /**
     * 검색 시 키워드(히스토리) 저장
     * @param command
     * @param userInfo
     */
    @Override
    @Transactional
    public void saveSearchHistory(SearchCommand.saveSearchHistory command, UserInfo.Main userInfo) {
        System.out.println("SearchServiceImpl :: saveSearchHistory");
        Long userId = userInfo.getId() != null ? userInfo.getId() : null;

        var history = searchReader.getSearchHistory(command);
        if(history.getId() == null) {
            var initHistory = command.toEntity(userId);
            searchStore.saveSearchHistory(initHistory);
            var keyword = searchReader.getSearchKeyword(command.getKeyword());
            if(keyword.getId() != null) keyword.setViews(keyword.getViews()+1);
        }

    }

    /**
     * 키워드 저장
     */
    @Override
    @Transactional
    public void saveSearchKeyword() {
        System.out.println("SearchServiceImpl :: saveSearchKeyword");

        var historyList = searchReader.getSearchHistoryList();
        for (var history : historyList) {
            var keyword = searchReader.getSearchKeyword(history.getKeyword());
            if(keyword.getId() == null) {
                System.out.println(history.getKeyword() + " 키워드 저장");
                var initKeyword = new Keyword(history.getKeyword());
                searchStore.saveSearchKeyword(initKeyword);
            } else {
                System.out.println(history.getKeyword() + " 키워드는 이미 존재함");
            }
        }
    }

    /**
     * 키워드 리스트 호출
     * @param keyword
     * @return
     */
    @Override
    public List<KeywordInfo> getSearchKeywordList(String keyword) {
        System.out.println("SearchServiceImpl :: getSearchKeywordList");
        var endKeyword = KeywordScanner.makeSearchKeyword(keyword);
        var keywordList = searchReader.getSearchKeywordList(keyword, endKeyword);
        return keywordList.stream().map(KeywordInfo::new).collect(Collectors.toList());
    }

    /**
     * 검색 키워드 비공개 처리
     * @param keyword
     * @param isPublic
     */
    @Override
    @Transactional
    public void setKeywordPublic(String keyword, int isPublic) {
        System.out.println("SearchServiceImpl :: setKeywordPublic");

    }
}
