package com.muesli.music.interfaces.playlist;

import com.muesli.music.application.playlist.PlaylistFacade;
import com.muesli.music.common.response.CommonResponse;
import com.muesli.music.common.util.TokenGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/playlists")
public class PlaylistApiController {
    private final PlaylistFacade playlistFacade;
    private final PlaylistDtoMapper playlistDtoMapper;

    @PostMapping("/create")
    public CommonResponse registerPlaylist(@RequestBody @Valid PlaylistDto.RegisterPlaylist request, @RequestHeader(value="Authorization", defaultValue = "") String usertoken) {
        System.out.println("PlaylistApiController :: registerPlaylist");
        usertoken = TokenGenerator.getHeaderToken(usertoken);
        var playlistCommand = request.toCommand();
        var playlistInfo = playlistFacade.registerPlaylist(playlistCommand, usertoken);
        var response = playlistDtoMapper.of(playlistInfo);
        return CommonResponse.success(response);
    }
}