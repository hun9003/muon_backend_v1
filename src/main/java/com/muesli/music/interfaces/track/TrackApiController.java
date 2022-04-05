package com.muesli.music.interfaces.track;

import com.muesli.music.application.track.TrackFacade;
import com.muesli.music.common.response.CommonResponse;
import com.muesli.music.common.util.TokenGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tracks")
public class TrackApiController {
    private final TrackFacade trackFacade;
    private final TrackDtoMapper trackDtoMapper;

    /**
     * 트랙 정보
     * @param trackId
     * @param usertoken
     * @return
     */
    @GetMapping("/{id}")
    public CommonResponse retrieveTrack(@PathVariable("id") Long trackId, @RequestHeader(value="Authorization", defaultValue = "") String usertoken){
        System.out.println("TrackApiController :: retrieveTrack");
        usertoken = TokenGenerator.getHeaderToken(usertoken);
        var trackInfo = trackFacade.findTrackInfo(trackId, usertoken);
        var response = trackDtoMapper.of(trackInfo);
        return CommonResponse.success(response);
    }

    /**
     * 좋아하는 트랙 리스트
     * @param usertoken
     * @return
     */
    @GetMapping("/likeables")
    public CommonResponse retrieveLikeTrackList(@RequestHeader(value="Authorization", defaultValue = "") String usertoken,
                                                @PageableDefault(size = 100, page = 1) Pageable pageable) {
        System.out.println("LikeApiController :: retrieveLikeTrackList");
        usertoken = TokenGenerator.getHeaderToken(usertoken);
        var trackInfoList = trackFacade.retrieveLikeList(usertoken, pageable);
        var trackInfoDtoList = trackInfoList.stream().map(trackDtoMapper::ofItem).collect(Collectors.toList());
        var response = new TrackDto.TrackList(trackInfoDtoList);
        return CommonResponse.success(response);
    }

    /**
     * 곡 순위
     * @param pageable
     * @param date
     * @param type
     * @param genre
     * @return
     */
    @GetMapping("/rank")
    public CommonResponse retrieveTrackRank(@PageableDefault(size = 100, page = 1) Pageable pageable,
                                            @RequestParam(name = "date", required = false) String date,
                                            @RequestParam(name = "type", defaultValue = "now") String type,
                                            @RequestParam(name = "genre", required = false) Long genre) {
        System.out.println("LikeApiController :: retrieveTrackRank");
        var searchDto = new TrackDto.TrackRankList(date, type, genre, null);
        var command = searchDto.toCommand();
        var trackList = trackFacade.retrieveTrackRank(pageable, command);
        var response = new TrackDto.TrackRankList(date, type, genre, trackList.stream().map(trackDtoMapper::of).collect(Collectors.toList()));
        return CommonResponse.success(response);
    }

}
