package com.muesli.music.application.search;

import com.muesli.music.domain.album.AlbumInfo;
import com.muesli.music.domain.album.AlbumService;
import com.muesli.music.domain.artist.ArtistInfo;
import com.muesli.music.domain.artist.ArtistService;
import com.muesli.music.domain.search.SearchCommand;
import com.muesli.music.domain.track.TrackInfo;
import com.muesli.music.domain.track.TrackService;
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
    private final TrackService trackService;
    private final AlbumService albumService;
    private final ArtistService artistService;
    /*
    검색 요구사항

    - 키워드에 대한 결과 리스트 전송
     */
    /**
     * 트랙 키워드 검색 조회
     * @param command
     * @param pageable
     * @return
     */
    public Map<String, List> retrieveSearchAll(SearchCommand.SearchRequest command, Pageable pageable) {
        System.out.println("SearchFacade :: retrieveNewTrack");
        var searchMap = new HashMap<String, List>();
        var trackList = trackService.getSearchTrack(command, pageable);
        var albumList = albumService.getSearchAlbum(command, pageable);
        var artistList = artistService.getSearchArtist(command, pageable);
        var lyricsList = trackService.getSearchLyrics(command, pageable);
        searchMap.put("trackList", trackList);
        searchMap.put("albumList", albumList);
        searchMap.put("artistList", artistList);
        searchMap.put("lyricsList", lyricsList);
        return searchMap;
    }

    /**
     * 트랙 키워드 검색 조회
     * @param command
     * @param pageable
     * @return
     */
    public List<TrackInfo.SearchInfo> retrieveSearchTrack(SearchCommand.SearchRequest command, Pageable pageable) {
        System.out.println("SearchFacade :: retrieveNewTrack");
        return trackService.getSearchTrack(command, pageable);
    }

    /**
     * 앨범 키워드 검색 조회
     * @param command
     * @param pageable
     * @return
     */
    public List<AlbumInfo.SearchInfo> retrieveSearchAlbum(SearchCommand.SearchRequest command, Pageable pageable) {
        System.out.println("SearchFacade :: retrieveNewAlbum");
        return albumService.getSearchAlbum(command, pageable);
    }

    /**
     * 아티스트 키워드 검색 조회
     * @param command
     * @param pageable
     * @return
     */
    public List<ArtistInfo.SearchInfo> retrieveSearchArtist(SearchCommand.SearchRequest command, Pageable pageable) {
        System.out.println("SearchFacade :: retrieveNewArtist");
        return artistService.getSearchArtist(command, pageable);
    }

    /**
     * 가사 키워드 검색 조회
     * @param command
     * @param pageable
     * @return
     */
    public List<TrackInfo.SearchLyricsInfo> retrieveSearchLyrics(SearchCommand.SearchRequest command, Pageable pageable) {
        System.out.println("SearchFacade :: retrieveSearchLyrics");
        return trackService.getSearchLyrics(command, pageable);
    }

    /**
     * 타입별 컨텐츠 개수 조회
     * @param command
     * @param type
     * @return
     */
    public int getSearchCount(SearchCommand.SearchRequest command, String type) {
        System.out.println("SearchFacade :: retrieveSearchLyrics");
        switch (type) {
            case "track" : return trackService.getSearchTrackCount(command);
            case "album" : return albumService.getSearchAlbumCount(command);
            case "artist" : return artistService.getSearchArtistCount(command);
            case "lyrics" : return trackService.getSearchLyricsCount(command);
        }
        return 0;
    }
}
