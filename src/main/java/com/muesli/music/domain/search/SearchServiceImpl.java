package com.muesli.music.domain.search;

import com.google.common.collect.Lists;
import com.muesli.music.common.util.KeywordScanner;
import com.muesli.music.domain.search.keyword.Keyword;
import com.muesli.music.domain.search.keyword.KeywordInfo;
import com.muesli.music.domain.user.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {
    private final SearchReader searchReader;
    private final SearchStore searchStore;

    /**
     * 검색 시 키워드(히스토리) 저장
     * @param command 검색기록 데이터 객체
     * @param userInfo 유저 정보
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
                var initKeyword = new Keyword(history.getKeyword());
                searchStore.saveSearchKeyword(initKeyword);
            }
        }
    }

    /**
     * 키워드 리스트 호출
     * @param keyword 검색 키워드
     * @return 검색 정보 리스트 조회
     */
    @Override
    public List<KeywordInfo> getSearchKeywordList(String keyword) {
        System.out.println("SearchServiceImpl :: getSearchKeywordList");
        if (keyword.trim().equals("")) return Lists.newArrayList();

        Pattern p = Pattern.compile("[a-zA-Z0-9]");
        Matcher m = p.matcher(keyword);
        var keywordKo = KeywordScanner.getEnToKo(keyword);

        var endKeyword = KeywordScanner.makeSearchKeyword(keywordKo);
        var keywordList = searchReader.getSearchKeywordList(keywordKo, endKeyword);

        try {
            var keyword2 = KeywordScanner.makeSearchKeywordJong(keywordKo.substring(keywordKo.length()-1));
            keyword2 = keywordKo.substring(0, keywordKo.length()-1) + keyword2;
            var keywordList2 = searchReader.getSearchKeywordList(keyword2, KeywordScanner.makeSearchKeyword(keyword2));

            keywordList.addAll(keywordList2);
        } catch (Exception ignored) {

        }

        if(m.find()) {
            var keywordList3 = searchReader.getSearchKeywordList(keyword);
            keywordList.addAll(keywordList3);
        }

        var listSize = Math.min(keywordList.size(), 9);
        keywordList.subList(0, listSize);

        return keywordList.stream().map(KeywordInfo::new).collect(Collectors.toList());
    }

    /**
     * 검색 키워드 공개, 비공개 처리
     * @param keyword 검색 키워드
     * @param isPublic 공개 여부
     */
    @Override
    @Transactional
    public void setKeywordPublic(String keyword, int isPublic) {
        System.out.println("SearchServiceImpl :: setKeywordPublic");
        var initKeyword = searchReader.getSearchKeyword(keyword);
        initKeyword.setIsPublic(isPublic);
    }
}
