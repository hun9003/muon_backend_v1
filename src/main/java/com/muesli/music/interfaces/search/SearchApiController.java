package com.muesli.music.interfaces.search;

import com.muesli.music.application.search.SearchFacade;
import com.muesli.music.common.response.CommonResponse;
import com.muesli.music.common.util.ClientUtils;
import com.muesli.music.common.util.Constant;
import com.muesli.music.common.util.TokenGenerator;
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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    private final SearchDtoMapper searchDtoMapper;

    /**
     * 전체 검색 결과 페이지
     * @param keyword
     * @param type
     * @param pageable
     * @return
     */
    @GetMapping("/all")
    public CommonResponse retrieveAllSearch(@RequestHeader(value="Authorization", defaultValue = "") String usertoken,
                                            HttpServletRequest request,
                                            @RequestParam(name = "keyword", required = false) String keyword,
                                            @RequestParam(name = "type", defaultValue = "all") String type,
                                            @PageableDefault(size = 10, page = 1) Pageable pageable) {
        System.out.println("SearchApiController :: retrieveAllSearch");
        usertoken = TokenGenerator.getHeaderToken(usertoken);

        var Searchrequest = new SearchDto.SearchRequest(keyword, type);
        var command = Searchrequest.toCommand();

        command.setTrackCount(searchFacade.getSearchCount(command, Constant.Item.TRACK));
        command.setAlbumCount(searchFacade.getSearchCount(command, Constant.Item.ALBUM));
        command.setArtistCount(searchFacade.getSearchCount(command, Constant.Item.ARTIST));
        command.setLyricsCount(searchFacade.getSearchCount(command, Constant.Item.LYRICS));

        var searchMap = searchFacade.retrieveSearchAll(command, pageable);

        var trackDtoList = ((List<TrackInfo.TrackListInfo>) searchMap.get("trackList")).stream().map(trackDtoMapper::of).collect(Collectors.toList());
        var albumDtoList = ((List<AlbumInfo.AlbumListInfo>) searchMap.get("albumList")).stream().map(albumDtoMapper::of).collect(Collectors.toList());
        var artistDtoList = ((List<ArtistInfo.SearchInfo>) searchMap.get("artistList")).stream().map(artistDtoMapper::of).collect(Collectors.toList());
        var lyricsDtoList = ((List<TrackInfo.SearchLyricsInfo>) searchMap.get("lyricsList")).stream().map(trackDtoMapper::of).collect(Collectors.toList());

        var trackSearchList = new SearchDto.SearchTrackResult(keyword, type, command.getTrackCount(), trackDtoList);
        var albumSearchList = new SearchDto.SearchAlbumResult(keyword, type, command.getAlbumCount(), albumDtoList);
        var artistSearchList = new SearchDto.SearchArtistResult(keyword, type, command.getArtistCount(), artistDtoList);
        var lyricsSearchList = new SearchDto.SearchLyricsResult(keyword, type, command.getLyricsCount(), lyricsDtoList);

        int resultCount = 0;
        if (trackSearchList.getTrackList().size() > 0 || albumSearchList.getAlbumList().size() > 0 || artistSearchList.getArtistList().size() > 0 || lyricsSearchList.getLyricsList().size() > 0) {
            resultCount = 1;
        }


        var ip = ClientUtils.getRemoteIP(request);
        var historyDto = new SearchDto.SearchHistory(keyword, ip, resultCount);
        searchFacade.saveSearchHistory(historyDto.toCommand(), usertoken);


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
    @GetMapping("/track")
    public CommonResponse retrieveTrackSearch(@RequestParam(name = "keyword", required = false) String keyword,
                                              @RequestParam(name = "type", defaultValue = Constant.Order.POPULARITY) String type,
                                              @PageableDefault(size = 100, page = 1) Pageable pageable) {
        System.out.println("SearchApiController :: retrieveTrackSearch");

        var request = new SearchDto.SearchRequest(keyword, type);
        var command = request.toCommand();
        var count = searchFacade.getSearchCount(command, Constant.Item.TRACK);
        command.setTrackCount(count);
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
    @GetMapping("/album")
    public CommonResponse retrieveAlbumSearch(@RequestParam(name = "keyword", required = false) String keyword,
                                              @RequestParam(name = "type", defaultValue = Constant.Order.POPULARITY) String type,
                                              @PageableDefault(size = 100, page = 1) Pageable pageable) {
        System.out.println("SearchApiController :: retrieveAlbumSearch");
        var request = new SearchDto.SearchRequest(keyword, type);
        var command = request.toCommand();
        var count = searchFacade.getSearchCount(command, Constant.Item.ALBUM);
        command.setAlbumCount(count);
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
    @GetMapping("/artist")
    public CommonResponse retrieveArtistSearch(@RequestParam(name = "keyword", required = false) String keyword,
                                              @RequestParam(name = "type", defaultValue = Constant.Order.POPULARITY) String type,
                                              @PageableDefault(size = 100, page = 1) Pageable pageable) {
        System.out.println("SearchApiController :: retrieveArtistSearch");
        var request = new SearchDto.SearchRequest(keyword, type);
        var command = request.toCommand();
        var count = searchFacade.getSearchCount(command, "artist");
        command.setArtistCount(count);
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
    public CommonResponse retrieveLyricsSearch(@RequestParam(name = "keyword", required = false) String keyword,
                                               @RequestParam(name = "type", defaultValue = Constant.Order.SIMILAR) String type,
                                               @PageableDefault(size = 100, page = 1) Pageable pageable) {
        System.out.println("SearchApiController :: retrieveLyricsSearch");
        var request = new SearchDto.SearchRequest(keyword, type);
        var command = request.toCommand();
        var count = searchFacade.getSearchCount(command, "lyrics");
        command.setLyricsCount(count);
        var LyricsList = searchFacade.retrieveSearchLyrics(command, pageable);
        var LyricsDtoList = LyricsList.stream().map(trackDtoMapper::of).collect(Collectors.toList());
        var response = new SearchDto.SearchLyricsResult(keyword, type, count, LyricsDtoList);
        return CommonResponse.success(response);
    }

    /**
     * 검색 자동완성 리스트
     * @param keyword
     * @return
     */
    @GetMapping
    public CommonResponse searchKeywordList(@RequestParam(name = "keyword") String keyword) {
        System.out.println("SearchApiController :: retrieveLyricsSearch");
        var keywordInfoList = searchFacade.getSearchKeywordList(keyword);
        var response = keywordInfoList.stream().map(searchDtoMapper::of).collect(Collectors.toList());
        return CommonResponse.success(response);
    }

}
