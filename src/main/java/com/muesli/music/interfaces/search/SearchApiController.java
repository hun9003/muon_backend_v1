package com.muesli.music.interfaces.search;

import com.muesli.music.application.track.TrackFacade;
import com.muesli.music.common.response.CommonResponse;
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

    private final TrackFacade trackFacade;
    private final TrackDtoMapper trackDtoMapper;

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
        System.out.println("LikeApiController :: retrieveTrackSearch");
        var request = new SearchDto.SearchRequest(keyword, type);
        var command = request.toCommand();
        var trackList = trackFacade.retrieveSearchTrack(command, pageable);
        var trackDtoList = trackList.stream().map(trackDtoMapper::of).collect(Collectors.toList());
        var response = new SearchDto.SearchTrackResult(keyword, type, trackDtoList);
        return CommonResponse.success(response);
    }

}
