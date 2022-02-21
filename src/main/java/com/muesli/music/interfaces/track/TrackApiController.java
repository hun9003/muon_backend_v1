package com.muesli.music.interfaces.track;

import com.muesli.music.application.track.TrackFacade;
import com.muesli.music.common.response.CommonResponse;
import com.muesli.music.common.util.TokenGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public CommonResponse retrieveLikeTrackList(@RequestHeader(value="Authorization", defaultValue = "") String usertoken) {
        System.out.println("LikeApiController :: retrieveLikeTrackList");
        usertoken = TokenGenerator.getHeaderToken(usertoken);
        var trackInfoList = trackFacade.retrieveLikeList(usertoken);
        var trackInfoDtoList = trackInfoList.stream().map(trackDtoMapper::ofItem).collect(Collectors.toList());
        var response = new TrackDto.TrackList(trackInfoDtoList);
        return CommonResponse.success(response);
    }

}
