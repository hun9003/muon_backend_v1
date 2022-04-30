package com.muesli.music.domain.genre;

import java.util.List;

public interface GenreReader {

    // 장르 대분류 리스트 호출
    List<Genre> getGenreParentList();

    // 장르 호출
    Genre getGenreById(Long genreId);

    // 장르 대분류에 속한 소분류 개수 호출
    int getGenreCountByParent(Long genreId);

    // 장르별 아이템 리스트 키워드를 통해 호출
    List<Genre> getGenreItemList(Long genreId, String start, String end);
}
