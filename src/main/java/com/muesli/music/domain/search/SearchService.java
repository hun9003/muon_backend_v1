package com.muesli.music.domain.search;

import com.muesli.music.domain.search.keyword.KeywordInfo;
import com.muesli.music.domain.user.UserInfo;

import java.util.List;

public interface SearchService {

    // 검색 키워드 저장
    void saveSearchHistory(SearchCommand.saveSearchHistory command, UserInfo.Main userInfo);

    // 검색 키워드 저장
    void saveSearchKeyword();

    // 검색기록 정보 리스트 조회
    List<KeywordInfo> getSearchKeywordList(String keyword);

    // 키워드 공개 여부 변경
    void setKeywordPublic(String keyword, int isPublic);

}
