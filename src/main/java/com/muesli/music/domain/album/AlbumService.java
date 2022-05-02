package com.muesli.music.domain.album;

import com.muesli.music.domain.genre.GenreInfo;
import com.muesli.music.domain.search.SearchCommand;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AlbumService {

    // 앨범 정보 호출
    AlbumInfo.Main findAlbumInfo(Long albumId);

    // 좋아하는 앨범 리스트 호출
    List<AlbumInfo.Main> getLikeList(String usertoken, Pageable pageable);

    // 최신 앨범 리스트 호출
    List<AlbumInfo.NewestAlbumInfo> getNewAlbum(Pageable pageable);

    // 앨범 검색 결과 호출
    List<AlbumInfo.SearchInfo> getSearchAlbum(SearchCommand.SearchRequest command, Pageable pageable);

    // 앨범 검색 결과 개수 호출
    int getSearchAlbumCount(SearchCommand.SearchRequest command);

    // 장르별 앨범 리스트 호출
    List<AlbumInfo.GenreAlbumInfo> getGenreAlbumList(Long genreId, Pageable pageable);

    // 장르별 앨범 리스트 호출 (전체결과)
    List<GenreInfo.Main> getGenreAlbumListAll(Pageable pageable);
}
