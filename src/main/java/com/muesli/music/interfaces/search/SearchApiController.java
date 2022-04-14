package com.muesli.music.interfaces.search;

import com.muesli.music.application.search.SearchFacade;
import com.muesli.music.common.response.CommonResponse;
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
        var trackList = searchFacade.retrieveSearchTrack(command, pageable);
        var trackDtoList = trackList.stream().map(trackDtoMapper::of).collect(Collectors.toList());
        var response = new SearchDto.SearchTrackResult(keyword, type, trackDtoList);
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
        var AlbumList = searchFacade.retrieveSearchAlbum(command, pageable);
        var AlbumDtoList = AlbumList.stream().map(albumDtoMapper::of).collect(Collectors.toList());
        var response = new SearchDto.SearchAlbumResult(keyword, type, AlbumDtoList);
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
        var ArtistList = searchFacade.retrieveSearchArtist(command, pageable);
        var ArtistDtoList = ArtistList.stream().map(artistDtoMapper::of).collect(Collectors.toList());
        var response = new SearchDto.SearchArtistResult(keyword, type, ArtistDtoList);
        return CommonResponse.success(response);
    }

}
