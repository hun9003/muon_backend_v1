package com.muesli.music.interfaces.album;

import com.muesli.music.application.album.AlbumFacade;
import com.muesli.music.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public CommonResponse findAlbum(@PathVariable("id") Long albumId) {
        var albumInfo = albumFacade.findAlbumInfo(albumId);
        var response = albumDtoMapper.of(albumInfo);
        return CommonResponse.success(response);
    }
}
