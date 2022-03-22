package com.muesli.music.application.track;

import com.muesli.music.domain.track.TrackInfo;
import com.muesli.music.domain.track.TrackService;
import com.muesli.music.domain.user.token.UsertokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrackFacade {
    private final TrackService trackService;
    private final UsertokenService usertokenService;

    /**
     * 트랙 정보 조회
     * @param trackId
     * @param token
     * @return
     */
    public TrackInfo.Main findTrackInfo(Long trackId, String token) {
        System.out.println("TrackFacade :: findTrackInfo");
        // 유저 토큰 조회
        var usertokenInfo = usertokenService.findUsertokenInfo(token);
        // 트랙 조회
        return trackService.findTrackInfo(trackId, usertokenInfo.getUserInfo());
    }


    /**
     * 좋아요 트랙 리스트 조회
     * @param token
     * @return
     */
    public List<TrackInfo.Main> retrieveLikeList(String token, Pageable pageable) {
        System.out.println("TrackFacade :: retrieveLikeList");
        usertokenService.checkUsertoken(token);
        return trackService.getLikeList(token, pageable);
    }
}
