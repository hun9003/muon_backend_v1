package com.muesli.music.interfaces.playlist;

import com.muesli.music.application.playlist.PlaylistFacade;
import com.muesli.music.common.response.CommonResponse;
import com.muesli.music.common.util.TokenGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/playlist")
public class PlaylistApiController {
    private final PlaylistFacade playlistFacade;
    private final PlaylistDtoMapper playlistDtoMapper;

    /**
     * 플레이리스트 정보 조회
     * @param playlistId
     * @param usertoken
     * @param pageable
     * @return
     */
    @GetMapping("/{id}")
    public CommonResponse retrievePlaylist(@PathVariable("id") Long playlistId, @RequestHeader(value="Authorization", defaultValue = "") String usertoken,
                                           @PageableDefault(size = 100, page = 1) Pageable pageable) {
        System.out.println("PlaylistApiController :: registerPlaylist");
        usertoken = TokenGenerator.getHeaderToken(usertoken);
        var playlistInfo = playlistFacade.findPlaylistInfo(playlistId, pageable, usertoken);
        var response = playlistDtoMapper.of(playlistInfo);
        return CommonResponse.success(response);
    }

    /**
     * 나의 플레이리스트 목록 조회
     * @param usertoken
     * @param pageable
     * @return
     */
    @GetMapping("/my")
    public CommonResponse retrieveMyPlaylist(@RequestHeader(value="Authorization", defaultValue = "") String usertoken,
                                             @PageableDefault(size = 100, page = 1) Pageable pageable) {
        System.out.println("PlaylistApiController :: registerPlaylist");
        usertoken = TokenGenerator.getHeaderToken(usertoken);
        var playlistInfoList = playlistFacade.retrieveMyPlaylist(usertoken, pageable);
        var response = playlistInfoList.stream().map(playlistDtoMapper::of).collect(Collectors.toList());
        return CommonResponse.success(response);
    }

    /**
     * 좋아하는 플레이리스트 목록 조회
     * @param usertoken
     * @param pageable
     * @return
     */
    @GetMapping("/likeables")
    public CommonResponse retrieveLikePlaylistList(@RequestHeader(value="Authorization", defaultValue = "") String usertoken,
                                                   @PageableDefault(size = 100, page = 1) Pageable pageable) {
        System.out.println("PlaylistApiController :: registerPlaylist");
        usertoken = TokenGenerator.getHeaderToken(usertoken);
        var playlistInfoList = playlistFacade.retrieveLikeList2(usertoken, pageable);
        var response = playlistInfoList.stream().map(playlistDtoMapper::of).collect(Collectors.toList());
        return CommonResponse.success(response);
    }

    /**
     * 플레이 리스트 생성
     * @param request
     * @param usertoken
     * @return
     */
    @PostMapping
    public CommonResponse registerPlaylist(@RequestBody @Valid PlaylistDto.RegisterPlaylist request, @RequestHeader(value="Authorization", defaultValue = "") String usertoken) {
        System.out.println("PlaylistApiController :: registerPlaylist");
        usertoken = TokenGenerator.getHeaderToken(usertoken);
        var playlistCommand = request.toCommand();
        var playlistInfo = playlistFacade.registerPlaylist(playlistCommand, usertoken);
        var response = playlistDtoMapper.of(playlistInfo);
        return CommonResponse.success(response);
    }

    /**
     * 플레이 리스트 수정
     * @param request
     * @param usertoken
     * @return
     */
    @PutMapping("/{id}")
    public CommonResponse updatePlaylist(@RequestBody @Valid PlaylistDto.UpdatePlaylist request, @RequestHeader(value="Authorization", defaultValue = "") String usertoken, @PathVariable(value = "id") Long playlistId) {
        System.out.println("PlaylistApiController :: updatePlaylist");
        request.setId(playlistId);
        usertoken = TokenGenerator.getHeaderToken(usertoken);
        var playlistCommand = request.toCommand();
        playlistFacade.updatePlaylist(playlistCommand, usertoken);
        return CommonResponse.success("OK");
    }

    /**
     * 플레이 리스트 삭제
     * @param usertoken
     * @param playlistId
     * @return
     */
    @DeleteMapping("/{id}")
    public CommonResponse removePlaylist(@RequestHeader(value = "Authorization", defaultValue = "") String usertoken, @PathVariable(value = "id") Long playlistId) {
        System.out.println("PlaylistApiController :: removePlaylist");
        usertoken = TokenGenerator.getHeaderToken(usertoken);
        playlistFacade.removePlaylist(playlistId, usertoken);
        return CommonResponse.success("OK");
    }

    /**
     * 플레이 리스트에 트랙 추가
     * @param request
     * @param usertoken
     * @return
     */
    @PutMapping("/track/{playlistId}")
    public CommonResponse addPlaylistTracks(@RequestBody @Valid PlaylistDto.PlaylistTracksRequest request, @RequestHeader(value = "Authorization", defaultValue = "") String usertoken, @PathVariable(value = "playlistId") Long playlistId) {
        System.out.println("PlaylistApiController :: addPlaylistTracks");
        request.setPlaylistId(playlistId);
        usertoken = TokenGenerator.getHeaderToken(usertoken);
        var command = playlistDtoMapper.of(request);
        playlistFacade.addTrackToPlaylist(command, usertoken);
        return CommonResponse.success("OK");
    }

    /**
     * 플레이 리스트 트랙 삭제
     * @param request
     * @param usertoken
     * @return
     */
    @DeleteMapping("/track/{playlistId}")
    public CommonResponse removePlaylistTracks(@RequestBody @Valid PlaylistDto.PlaylistTracksRequest request, @RequestHeader(value = "Authorization", defaultValue = "") String usertoken, @PathVariable(value = "playlistId") Long playlistId) {
        System.out.println("PlaylistApiController :: removePlaylistTracks");
        request.setPlaylistId(playlistId);
        usertoken = TokenGenerator.getHeaderToken(usertoken);
        var command = playlistDtoMapper.of(request);
        playlistFacade.removeTrackToPlaylist(command, usertoken);
        return CommonResponse.success("OK");
    }
}
