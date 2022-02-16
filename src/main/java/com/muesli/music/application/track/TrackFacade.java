package com.muesli.music.application.track;

import com.muesli.music.domain.track.TrackInfo;
import com.muesli.music.domain.track.TrackService;
import com.muesli.music.domain.user.token.UsertokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrackFacade {
    private final TrackService trackService;
    private final UsertokenService usertokenService;

    public TrackInfo.Main findTrackInfo(Long trackId, String usertoken) {
        System.out.println("TrackFacade :: findTrackInfo");
        // 유저 토큰 조회
        var usertokenInfo = usertokenService.findUsertokenInfo(usertoken);
        // 트랙 조회
        return trackService.findTrackInfo(trackId, usertokenInfo.getUserInfo());
    }
}
