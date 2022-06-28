package com.muesli.music.application.player;

import com.muesli.music.domain.track.TrackService;
import com.muesli.music.domain.user.token.UsertokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlayerFacade {
    private final UsertokenService usertokenService;
    private final TrackService trackService;

//    /**
//     * 곡 재생 키 생성 및 전달
//     * @param metadata 키 생성을 위한 데이터 객체
//     * @param token 유저 토큰
//     * @return 곡 재생을 위한 키
//     */
//    public String getPlayerKey(Map<String, Object> metadata, String token) {
//        System.out.println("PlayerFacade :: getPlayerKey");
//        // 토큰을 통해 유저 토큰 정보 호출
//        var usertoken = usertokenService.findUsertokenInfo(token);
//
//        // 키 생성에 필요한 유저 idx 호출
//        var userId = usertoken.getUserInfo().getId() != null ? usertoken.getUserInfo().getId() : 0;
//
//        // 객체에 저장된 트랙 idx 유효성 검사
//        var track = trackService.findTrackInfo((Long) metadata.get("t"));
//
//        // 객체에 유저 idx 저장
//        metadata.put("u", userId);
//
//        var url = "";
//        // 트랙 정보가 올바르지 않을 시 오류 전달
//        if(track.getUrl() == null) throw new BaseException(ErrorCode.COMMON_INVALID_PARAMETER);
//
//        // 트랙 URL 가공
//        if(track.getUrl().contains("/storage/track_media/beta/")) {
//            url = track.getUrl().replace("/storage/track_media/beta/", "https://music1.muonmusic.com/beta/migration/");
//        } else if(track.getUrl().contains("/mnt/music/data/")) {
//            url = track.getUrl().replace("/mnt/music/data/", "https://music1.muonmusic.com/");
//        }
//
//        // 곡 재생을 위해 URL 가공 및 키 삽입
//        try {
//            url += "?key=" +ClientUtils.getPlayerKey(metadata);
//        } catch (Exception e) {
//            throw new BaseException(ErrorCode.COMMON_INVALID_PARAMETER);
//        }
//        return url;
//    }
}
