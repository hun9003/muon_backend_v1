package com.muesli.music.interfaces.player;

import com.muesli.music.application.player.PlayerFacade;
import com.muesli.music.common.response.CommonResponse;
import com.muesli.music.common.util.ClientUtils;
import com.muesli.music.common.util.TokenGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/player")
public class PlayerApiController {
    private final PlayerFacade playerFacade;

    /**
     * 플레이 로그 생성
     * @param trackId
     * @param usertoken
     * @param request
     * @return
     */
    @PostMapping("/{id}")
    public CommonResponse createPlayerLog(@PathVariable("id") Long trackId,
                                            @RequestHeader(value="Authorization", defaultValue = "") String usertoken, HttpServletRequest request) {
        System.out.println("PlayerApiController :: createPlayerLog");
        usertoken = TokenGenerator.getHeaderToken(usertoken);
        System.out.println(usertoken);
        var userAgent = request.getHeader("User-Agent");
        var ip = ClientUtils.getRemoteIP(request);
        var playLogDtoRequest = new PlayerDto.RegisterPlayLog(trackId, userAgent, ip);
        playerFacade.registerPlaylog(playLogDtoRequest, usertoken);
        return CommonResponse.success("OK");
    }
}
