package com.muesli.music.domain.album;

import com.muesli.music.domain.genre.GenreInfo;
import com.muesli.music.domain.search.SearchCommand;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AlbumService {
    AlbumInfo.Main findAlbumInfo(Long albumId);

    List<AlbumInfo.Main> getLikeList(String usertoken, Pageable pageable);

    List<AlbumInfo.NewestAlbumInfo> getNewAlbum(Pageable pageable);

    List<AlbumInfo.SearchInfo> getSearchAlbum(SearchCommand.SearchRequest command, Pageable pageable);

    int getSearchAlbumCount(SearchCommand.SearchRequest command);

    List<AlbumInfo.GenreAlbumInfo> getGenreAlbumList(Long genreId, Pageable pageable);

    List<GenreInfo.Main> getGenreAlbumListAll(Pageable pageable);
}
