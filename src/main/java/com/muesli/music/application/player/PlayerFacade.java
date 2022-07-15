package com.muesli.music.application.player;

import com.muesli.music.domain.playlog.PlaylogService;
import com.muesli.music.domain.track.TrackService;
import com.muesli.music.domain.user.token.UsertokenService;
import com.muesli.music.interfaces.player.PlayerDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlayerFacade {
    private final UsertokenService usertokenService;
    private final TrackService trackService;
    private final PlaylogService playlogService;

    public void registerPlaylog(PlayerDto.RegisterPlayLog playLogDtoRequest, String usertoken) {
        System.out.println("PlayerFacade :: registerPlaylog");
        var userInfo = usertokenService.findUsertokenInfo(usertoken);
        var track = trackService.findTrackInfo(playLogDtoRequest.getTrackId());
        var command = playLogDtoRequest.toCommand(track.getAlbumInfo().getId(), userInfo.getUserInfo().getId());
        playlogService.creagePlaylog(command);
    }
}
