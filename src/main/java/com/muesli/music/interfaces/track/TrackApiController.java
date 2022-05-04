package com.muesli.music.interfaces.track;

import com.muesli.music.application.track.TrackFacade;
import com.muesli.music.common.response.CommonResponse;
import com.muesli.music.common.response.ErrorCode;
import com.muesli.music.common.util.Constant;
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
@RequestMapping("/api/v1/track")
public class TrackApiController {
    private final TrackFacade trackFacade;
    private final TrackDtoMapper trackDtoMapper;

    /**
     * 트랙 정보
     * @param trackId
     * @return
     */
    @GetMapping("/{id}")
    public CommonResponse retrieveTrack(@PathVariable("id") Long trackId){
        System.out.println("TrackApiController :: retrieveTrack");
        var trackInfo = trackFacade.findTrackInfo(trackId);
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
        var trackInfoList = trackFacade.retrieveLikeList2(usertoken, pageable);
        var trackDtoList = trackInfoList.stream().map(trackDtoMapper::of).collect(Collectors.toList());
        var response = new TrackDto.TrackInfoList(trackDtoList);
        return CommonResponse.success(response);
    }

    /**
     * 곡 순위
     * @param pageable
     * @param date
     * @param genre
     * @return
     */
    @GetMapping("/rank/{type}")
    public CommonResponse retrieveTrackRank(@PageableDefault(size = 100, page = 1) Pageable pageable,
                                            @RequestParam(name = "date", required = false) String date,
                                            @RequestParam(name = "genre", required = false) Long genre,
                                            @PathVariable(value = "type") String type) {
        System.out.println("LikeApiController :: retrieveTrackRank");
        if(!type.equals("now") && !type.equals("day") && !type.equals("week") && !type.equals("month"))
            return CommonResponse.fail(ErrorCode.COMMON_INVALID_PARAMETER);

        var layout = trackFacade.getChartLayout(type);
        var searchDto = new TrackDto.TrackRankList(date, type, genre, layout, null);
        var command = searchDto.toCommand();
        var trackList = trackFacade.retrieveTrackRank(pageable, command);
        if(trackList.size() > 0) {
            date = trackList.get(0).getDate();
        }

        var response = new TrackDto.TrackRankList(date, type, genre, layout, trackList.stream().map(trackDtoMapper::of).collect(Collectors.toList()));
        return CommonResponse.success(response);
    }

    /**
     * 최신 곡
     * @param pageable
     * @return
     */
    @GetMapping("/new")
    public CommonResponse retrieveNewTrack(@PageableDefault(size = 50, page = 1) Pageable pageable) {
        System.out.println("TrackApiController :: retrieveNewTrack");
        var trackList = trackFacade.retrieveNewTrack(pageable);
        var trackDtoList = trackList.stream().map(trackDtoMapper::of).collect(Collectors.toList());
        var response = new TrackDto.NewestTrackList(Constant.Item.TRACK, trackDtoList);
        return CommonResponse.success(response);
    }

    /**
     * 최근 들은 곡
     * @param pageable
     * @return
     */
    @GetMapping("/history")
    public CommonResponse retrieveUserHistoryTrack(@RequestHeader(value="Authorization", defaultValue = "") String usertoken,
                                                   @PageableDefault(size = 50, page = 1) Pageable pageable) {
        System.out.println("TrackApiController :: retrieveUserHistoryTrack");
        usertoken = TokenGenerator.getHeaderToken(usertoken);
        var trackList = trackFacade.retrieveUserHistoryTrack(usertoken, pageable);
        var trackDtoList = trackList.stream().map(trackDtoMapper::of).collect(Collectors.toList());
        var response = new TrackDto.HistoryTrackList(Constant.Item.TRACK, trackDtoList);
        return CommonResponse.success(response);
    }

}
