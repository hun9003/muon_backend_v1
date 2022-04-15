package com.muesli.music.interfaces.search;

import com.muesli.music.application.search.SearchFacade;
import com.muesli.music.common.response.CommonResponse;
import com.muesli.music.domain.album.AlbumInfo;
import com.muesli.music.domain.artist.ArtistInfo;
import com.muesli.music.domain.track.TrackInfo;
import com.muesli.music.interfaces.album.AlbumDtoMapper;
import com.muesli.music.interfaces.artist.ArtistDtoMapper;
import com.muesli.music.interfaces.track.TrackDtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/search")
public class SearchApiController {

    private final SearchFacade searchFacade;
    private final TrackDtoMapper trackDtoMapper;
    private final AlbumDtoMapper albumDtoMapper;
    private final ArtistDtoMapper artistDtoMapper;

    /**
     * 전체 검색 결과 페이지
     * @param keyword
     * @param type
     * @param pageable
     * @return
     */
    @GetMapping("/all")
    public CommonResponse retrieveAllSearch(@RequestParam(name = "keyword") String keyword,
                                              @RequestParam(name = "type", defaultValue = "all") String type,
                                              @PageableDefault(size = 10, page = 1) Pageable pageable) {
        System.out.println("SearchApiController :: retrieveAllSearch");
        var request = new SearchDto.SearchRequest(keyword, type);
        var command = request.toCommand();

        command.setTrackCount(searchFacade.getSearchCount(command, "track"));
        command.setAlbumCount(searchFacade.getSearchCount(command, "album"));
        command.setArtistCount(searchFacade.getSearchCount(command, "artist"));
        command.setLyricsCount(searchFacade.getSearchCount(command, "lyrics"));

        var searchMap = searchFacade.retrieveSearchAll(command, pageable);

        var trackDtoList = ((List<TrackInfo.SearchInfo>) searchMap.get("trackList")).stream().map(trackDtoMapper::of).collect(Collectors.toList());
        var albumDtoList = ((List<AlbumInfo.SearchInfo>) searchMap.get("albumList")).stream().map(albumDtoMapper::of).collect(Collectors.toList());
        var artistDtoList = ((List<ArtistInfo.SearchInfo>) searchMap.get("artistList")).stream().map(artistDtoMapper::of).collect(Collectors.toList());
        var lyricsDtoList = ((List<TrackInfo.SearchLyricsInfo>) searchMap.get("lyricsList")).stream().map(trackDtoMapper::of).collect(Collectors.toList());

        var trackSearchList = new SearchDto.SearchTrackResult(keyword, type, command.getTrackCount(), trackDtoList);
        var albumSearchList = new SearchDto.SearchAlbumResult(keyword, type, command.getAlbumCount(), albumDtoList);
        var artistSearchList = new SearchDto.SearchArtistResult(keyword, type, command.getArtistCount(), artistDtoList);
        var lyricsSearchList = new SearchDto.SearchLyricsResult(keyword, type, command.getLyricsCount(), lyricsDtoList);

        var response = new SearchDto.SearchAllResult(keyword, type, trackSearchList, albumSearchList, artistSearchList, lyricsSearchList);
        return CommonResponse.success(response);
    }

    /**
     * 트랙 검색 결과 페이지
     * @param keyword
     * @param type
     * @param pageable
     * @return
     */
    @GetMapping("/tracks")
    public CommonResponse retrieveTrackSearch(@RequestParam(name = "keyword") String keyword,
                                              @RequestParam(name = "type", defaultValue = "popularity") String type,
                                              @PageableDefault(size = 100, page = 1) Pageable pageable) {
        System.out.println("SearchApiController :: retrieveTrackSearch");
        var request = new SearchDto.SearchRequest(keyword, type);
        var command = request.toCommand();
        var count = searchFacade.getSearchCount(command, "track");
        var trackList = searchFacade.retrieveSearchTrack(command, pageable);
        var trackDtoList = trackList.stream().map(trackDtoMapper::of).collect(Collectors.toList());
        var response = new SearchDto.SearchTrackResult(keyword, type, count, trackDtoList);
        return CommonResponse.success(response);
    }

    /**
     * 앨범 검색 결과 페이지
     * @param keyword
     * @param type
     * @param pageable
     * @return
     */
    @GetMapping("/albums")
    public CommonResponse retrieveAlbumSearch(@RequestParam(name = "keyword") String keyword,
                                              @RequestParam(name = "type", defaultValue = "popularity") String type,
                                              @PageableDefault(size = 100, page = 1) Pageable pageable) {
        System.out.println("SearchApiController :: retrieveAlbumSearch");
        var request = new SearchDto.SearchRequest(keyword, type);
        var command = request.toCommand();
        var count = searchFacade.getSearchCount(command, "album");
        var AlbumList = searchFacade.retrieveSearchAlbum(command, pageable);
        var AlbumDtoList = AlbumList.stream().map(albumDtoMapper::of).collect(Collectors.toList());
        var response = new SearchDto.SearchAlbumResult(keyword, type, count, AlbumDtoList);
        return CommonResponse.success(response);
    }

    /**
     * 아티스트 검색 결과 페이지
     * @param keyword
     * @param type
     * @param pageable
     * @return
     */
    @GetMapping("/artists")
    public CommonResponse retrieveArtistSearch(@RequestParam(name = "keyword") String keyword,
                                              @RequestParam(name = "type", defaultValue = "popularity") String type,
                                              @PageableDefault(size = 100, page = 1) Pageable pageable) {
        System.out.println("SearchApiController :: retrieveArtistSearch");
        var request = new SearchDto.SearchRequest(keyword, type);
        var command = request.toCommand();
        var count = searchFacade.getSearchCount(command, "artist");
        var ArtistList = searchFacade.retrieveSearchArtist(command, pageable);
        var ArtistDtoList = ArtistList.stream().map(artistDtoMapper::of).collect(Collectors.toList());
        var response = new SearchDto.SearchArtistResult(keyword, type, count, ArtistDtoList);
        return CommonResponse.success(response);
    }

    /**
     * 가사 검색 결과 페이지
     * @param keyword
     * @param type
     * @param pageable
     * @return
     */
    @GetMapping("/lyrics")
    public CommonResponse retrieveLyricsSearch(@RequestParam(name = "keyword") String keyword,
                                               @RequestParam(name = "type", defaultValue = "similar") String type,
                                               @PageableDefault(size = 100, page = 1) Pageable pageable) {
        System.out.println("SearchApiController :: retrieveLyricsSearch");
        var request = new SearchDto.SearchRequest(keyword, type);
        var command = request.toCommand();
        var count = searchFacade.getSearchCount(command, "lyrics");
        var LyricsList = searchFacade.retrieveSearchLyrics(command, pageable);
        var LyricsDtoList = LyricsList.stream().map(trackDtoMapper::of).collect(Collectors.toList());
        var response = new SearchDto.SearchLyricsResult(keyword, type, count, LyricsDtoList);
        return CommonResponse.success(response);
    }

}
