package com.muesli.music.domain.genre;

import java.util.List;

public interface GenreReader {
    List<Genre> getGenreParentList();

    Genre getGenreById(Long genreId);

    int getGenreCountByParent(Long genreId);

    List<Genre> getGenreItemList(Long genreId, String start, String end);
}
