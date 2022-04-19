package com.muesli.music.application.player;

import com.muesli.music.common.exception.BaseException;
import com.muesli.music.common.response.ErrorCode;
import com.muesli.music.common.util.ClientUtils;
import com.muesli.music.domain.track.TrackService;
import com.muesli.music.domain.user.token.UsertokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlayerFacade {
    private final UsertokenService usertokenService;
    private final TrackService trackService;

    public String getPlayerKey(Map<String, Object> metadata, String token) {
        System.out.println("PlayerFacade :: getPlayerKey");
        var usertoken = usertokenService.findUsertokenInfo(token);
        var userId = usertoken.getUserInfo().getId() != null ? usertoken.getUserInfo().getId() : 0;

        var track = trackService.findTrackInfo((Long) metadata.get("t"));
        metadata.put("u", userId);
        var url = "";
        if(track.getUrl() == null) throw new BaseException(ErrorCode.COMMON_INVALID_PARAMETER);

        if(track.getUrl().contains("/storage/track_media/beta/")) {
            url = track.getUrl().replace("/storage/track_media/beta/", "https://music1.muonmusic.com/beta/migration/");
        } else if(track.getUrl().contains("/mnt/music/data/")) {
            url = track.getUrl().replace("/mnt/music/data/", "https://music1.muonmusic.com/");
        }

        try {
            url += "?key=" +ClientUtils.getPlayerKey(metadata);
        } catch (Exception e) {
            throw new BaseException(ErrorCode.COMMON_INVALID_PARAMETER);
        }
        return url;
    }
}
