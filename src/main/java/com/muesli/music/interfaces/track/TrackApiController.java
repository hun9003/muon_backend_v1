package com.muesli.music.interfaces.track;

import com.muesli.music.application.track.TrackFacade;
import com.muesli.music.common.response.CommonResponse;
import com.muesli.music.common.util.TokenGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tracks")
public class TrackApiController {
    private final TrackFacade trackFacade;
    private final TrackDtoMapper trackDtoMapper;

    @GetMapping("/{id}")
    public CommonResponse retrieveTrack(@PathVariable("id") Long trackId, @RequestHeader(value="Authorization", defaultValue = "") String usertoken){
        System.out.println("TrackApiController :: retrieveTrack");
        usertoken = TokenGenerator.getHeaderToken(usertoken);
        var trackInfo = trackFacade.findTrackInfo(trackId, usertoken);
        var response = trackDtoMapper.of(trackInfo);
        return CommonResponse.success(response);
    }
}
