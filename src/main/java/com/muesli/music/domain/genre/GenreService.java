package com.muesli.music.domain.genre;

import java.util.List;

public interface GenreService {

    //  대분류 리스트 호출
    List<GenreInfo.Main> getGenreParentList();

    //  대분류 / 소분류 알파벳, 자음별 리스트 호출 ( 장르 부모 없으면 아티스트 리스트 ) (인기순 최신순, 인기순 데뷔순)
    List<Object> getGenreItemList(GenreCommand.GenreRequest command);

}
