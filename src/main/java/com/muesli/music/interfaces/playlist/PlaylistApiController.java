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

    @GetMapping("/{id}")
    public CommonResponse retrievePlaylist(@PathVariable("id") Long playlistId, @RequestHeader(value="Authorization", defaultValue = "") String usertoken) {
        System.out.println("PlaylistApiController :: registerPlaylist");
        usertoken = TokenGenerator.getHeaderToken(usertoken);
        var playlistInfo = playlistFacade.findPlaylistInfo(playlistId, usertoken);
        var response = playlistDtoMapper.of(playlistInfo);
        return CommonResponse.success(response);
    }

    @PostMapping("/create")
    public CommonResponse registerPlaylist(@RequestBody @Valid PlaylistDto.RegisterPlaylist request, @RequestHeader(value="Authorization", defaultValue = "") String usertoken) {
        System.out.println("PlaylistApiController :: registerPlaylist");
        usertoken = TokenGenerator.getHeaderToken(usertoken);
        var playlistCommand = request.toCommand();
        var playlistInfo = playlistFacade.registerPlaylist(playlistCommand, usertoken);
        var response = playlistDtoMapper.of(playlistInfo);
        return CommonResponse.success(response);
    }

    @PostMapping("/update")
    public CommonResponse updatePlaylist(@RequestBody @Valid PlaylistDto.UpdatePlaylist request, @RequestHeader(value="Authorization", defaultValue = "") String usertoken) {
        System.out.println("PlaylistApiController :: updatePlaylist");
        usertoken = TokenGenerator.getHeaderToken(usertoken);
        var playlistCommand = request.toCommand();
        playlistFacade.updatePlaylist(playlistCommand, usertoken);
        return CommonResponse.success("OK");
    }

    @GetMapping("/delete/{id}")
    public CommonResponse removePlaylist(@RequestHeader(value = "Authorization", defaultValue = "") String usertoken, @PathVariable(value = "id") Long playlistId) {
        System.out.println("PlaylistApiController :: removePlaylist");
        usertoken = TokenGenerator.getHeaderToken(usertoken);
        playlistFacade.removePlaylist(playlistId, usertoken);
        return CommonResponse.success("OK");
    }
}
