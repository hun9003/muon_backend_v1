package com.muesli.music.domain.album;

import com.muesli.music.domain.genre.GenreInfo;
import com.muesli.music.domain.search.SearchCommand;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AlbumService {

    AlbumInfo.Main findAlbumInfo(Long albumId, Pageable pageable);

    // 좋아하는 앨범 리스트 호출
    List<AlbumInfo.AlbumListInfo> getLikeList2(String usertoken, Pageable pageable);

    // 최신 앨범 리스트 호출
    List<AlbumInfo.AlbumListInfo> getNewAlbum(Pageable pageable);

    // 앨범 검색 결과 호출
    List<AlbumInfo.AlbumListInfo> getSearchAlbum(SearchCommand.SearchRequest command, Pageable pageable);

    // 앨범 검색 결과 개수 호출
    int getSearchAlbumCount(SearchCommand.SearchRequest command);

    // 장르별 앨범 리스트 호출
    List<AlbumInfo.AlbumListInfo> getGenreAlbumList(Long genreId, Pageable pageable);

    // 장르별 앨범 리스트 호출 (전체결과)
    List<GenreInfo.Main> getGenreAlbumListAll(Pageable pageable);
    
    // 채널별 앨범 리스트 호출
    List<AlbumInfo.AlbumListInfo> getChannelAlbum(Long channelId, Pageable pageable);
}
