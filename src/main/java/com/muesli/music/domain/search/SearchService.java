package com.muesli.music.domain.search;

import com.muesli.music.domain.search.keyword.KeywordInfo;
import com.muesli.music.domain.user.UserInfo;

import java.util.List;

public interface SearchService {
    void saveSearchHistory(SearchCommand.saveSearchHistory command, UserInfo.Main userInfo);

    void saveSearchKeyword();

    List<KeywordInfo> getSearchKeywordList(String keyword);

    void setKeywordPublic(String keyword, int isPublic);

}
