package com.muesli.music.interfaces.album;

import com.muesli.music.application.album.AlbumFacade;
import com.muesli.music.common.response.CommonResponse;
import com.muesli.music.common.util.TokenGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/albums")
public class AlbumApiController {
    private final AlbumFacade albumFacade;
    private final AlbumDtoMapper albumDtoMapper;

    /**
     *
     * @param albumId
     * @return
     */
    @GetMapping("/{id}")
    public CommonResponse retrieveAlbum(@PathVariable("id") Long albumId, @RequestHeader(value="Authorization", defaultValue = "") String usertoken) {
        usertoken = TokenGenerator.getHeaderToken(usertoken);
        var albumInfo = albumFacade.findAlbumInfo(albumId, usertoken);
        var response = albumDtoMapper.of(albumInfo);
        return CommonResponse.success(response);
    }
}
