package com.muesli.music.application.search;

import com.muesli.music.common.util.Constant;
import com.muesli.music.domain.album.AlbumInfo;
import com.muesli.music.domain.album.AlbumService;
import com.muesli.music.domain.artist.ArtistInfo;
import com.muesli.music.domain.artist.ArtistService;
import com.muesli.music.domain.search.SearchCommand;
import com.muesli.music.domain.search.SearchService;
import com.muesli.music.domain.search.keyword.KeywordInfo;
import com.muesli.music.domain.track.TrackInfo;
import com.muesli.music.domain.track.TrackService;
import com.muesli.music.domain.user.token.UsertokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchFacade {
    private final SearchService searchService;
    private final TrackService trackService;
    private final AlbumService albumService;
    private final ArtistService artistService;
    private final UsertokenService usertokenService;

    /**
     * 키워드를 통해 전체(트랙, 앨범, 아티스트, 가사 정보) 검색
     * @param command 검색을 위한 데이터 객체
     * @param pageable 검색 결과 페이징 처리를 위한 객체
     * @return 전체 검색 결과 정보
     */
    public Map<String, List> retrieveSearchAll(SearchCommand.SearchRequest command, Pageable pageable) {
        System.out.println("SearchFacade :: retrieveNewTrack");
        var searchMap = new HashMap<String, List>();
        // 각 검색 키워드별 결과 리스트 호출
        var trackList = trackService.getSearchTrack(command, pageable);
        var albumList = albumService.getSearchAlbum(command, pageable);
        var artistList = artistService.getSearchArtist(command, pageable);
        var lyricsList = trackService.getSearchLyrics(command, pageable);

        // 객체에 결과 저장
        searchMap.put("trackList", trackList);
        searchMap.put("albumList", albumList);
        searchMap.put("artistList", artistList);
        searchMap.put("lyricsList", lyricsList);

        return searchMap;
    }

    /**
     * 트랙 키워드 검색 조회
     * @param command 검색을 위한 데이터 객체
     * @param pageable 검색 결과 페이징 처리를 위한 객체
     * @return 트랙 검색 결과 정보
     */
    public List<TrackInfo.TrackListInfo> retrieveSearchTrack(SearchCommand.SearchRequest command, Pageable pageable) {
        System.out.println("SearchFacade :: retrieveSearchTrack");
        return trackService.getSearchTrack(command, pageable);
    }

    /**
     * 앨범 키워드 검색 조회
     * @param command 검색을 위한 데이터 객체
     * @param pageable 검색 결과 페이징 처리를 위한 객체
     * @return 앨범 검색 결과 정보
     */
    public List<AlbumInfo.AlbumListInfo> retrieveSearchAlbum(SearchCommand.SearchRequest command, Pageable pageable) {
        System.out.println("SearchFacade :: retrieveSearchAlbum");
        return albumService.getSearchAlbum(command, pageable);
    }

    /**
     * 아티스트 키워드 검색 조회
     * @param command 검색을 위한 데이터 객체
     * @param pageable 검색 결과 페이징 처리를 위한 객체
     * @return 아티스트 검색 결과 정보
     */
    public List<ArtistInfo.ArtistListInfo> retrieveSearchArtist(SearchCommand.SearchRequest command, Pageable pageable) {
        System.out.println("SearchFacade :: retrieveSearchArtist");
        return artistService.getSearchArtist(command, pageable);
    }

    /**
     * 가사 키워드 검색 조회
     * @param command 검색을 위한 데이터 객체
     * @param pageable 검색 결과 페이징 처리를 위한 객체
     * @return 가사 검색 결과 정보
     */
    public List<TrackInfo.SearchLyricsInfo> retrieveSearchLyrics(SearchCommand.SearchRequest command, Pageable pageable) {
        System.out.println("SearchFacade :: retrieveSearchLyrics");
        return trackService.getSearchLyrics(command, pageable);
    }

    /**
     * 타입별 컨텐츠 개수 조회
     * @param command 검색을 위한 데이터 객체
     * @param type 검색 대상 타입 (트랙, 앨범, 아티스트, 가사)
     * @return
     */
    public int getSearchCount(SearchCommand.SearchRequest command, String type) {
        System.out.println("SearchFacade :: getSearchCount");
        // TODO 상수 저장 필요
        switch (type) {
            case Constant.Item.TRACK : return trackService.getSearchTrackCount(command);
            case Constant.Item.ALBUM : return albumService.getSearchAlbumCount(command);
            case Constant.Item.ARTIST : return artistService.getSearchArtistCount(command);
            case Constant.Item.LYRICS : return trackService.getSearchLyricsCount(command);
        }
        return 0;
    }

    /**
     * 검색 시 키워드 히스토리에 저장 후 키워드 조회수 증가
     * @param command 키워드 저장을 위한 데이터 객체
     * @param token 유저 토큰
     */
    public void saveSearchHistory(SearchCommand.saveSearchHistory command, String token) {
        System.out.println("SearchFacade :: saveSearchHistory");
        // 유저 토큰을 통해 유저토큰 정보 조회
        var usertoken = usertokenService.findUsertokenInfo(token);
        searchService.saveSearchHistory(command, usertoken.getUserInfo());
    }

    /**
     * 키워드 자동 완성을 위한 키워드 리스트
     * @param keyword 키워드
     * @return 키워드 정보 리스트
     */
    public List<KeywordInfo> getSearchKeywordList(String keyword) {
        System.out.println("SearchFacade :: getSearchKeywordList");
        return searchService.getSearchKeywordList(keyword);
    }
}
