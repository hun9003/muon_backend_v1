package com.muesli.music.interfaces.player;

import com.muesli.music.application.player.PlayerFacade;
import com.muesli.music.common.response.CommonResponse;
import com.muesli.music.common.util.TokenGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/player")
public class PlayerApiController {
    private final PlayerFacade playerFacade;

    @GetMapping("/{id}")
    public CommonResponse retrievePlayerKey(@PathVariable("id") Long trackId,
                                            @RequestHeader(value="Authorization", defaultValue = "") String usertoken,
                                            HttpServletRequest request) {
        System.out.println("PlayerApiController :: retrievePlayerKey");
        usertoken = TokenGenerator.getHeaderToken(usertoken);
        System.out.println(usertoken);
        var userAgent = request.getHeader("User-Agent");

        var metadatas = new HashMap<String, Object>();
        metadatas.put("t", trackId);
        metadatas.put("u", 0);
        metadatas.put("e", (int) (System.currentTimeMillis() / 1000) + 1200);
        metadatas.put("m", false);
        metadatas.put("a", userAgent);

        var response = new HashMap<String, Object>();
        response.put("id", trackId);
        response.put("url", playerFacade.getPlayerKey(metadatas, usertoken));

        return CommonResponse.success(response);
    }
}
