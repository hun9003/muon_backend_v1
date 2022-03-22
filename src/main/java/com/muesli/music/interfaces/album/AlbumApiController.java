package com.muesli.music.interfaces.album;

import com.muesli.music.application.album.AlbumFacade;
import com.muesli.music.common.response.CommonResponse;
import com.muesli.music.common.util.TokenGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/albums")
public class AlbumApiController {
    private final AlbumFacade albumFacade;
    private final AlbumDtoMapper albumDtoMapper;

    /**
     * 앨범 정보
     * @param albumId
     * @param usertoken
     * @return
     */
    @GetMapping("/{id}")
    public CommonResponse retrieveAlbum(@PathVariable("id") Long albumId, @RequestHeader(value="Authorization", defaultValue = "") String usertoken) {
        System.out.println("AlbumApiController :: retrieveAlbum");
        usertoken = TokenGenerator.getHeaderToken(usertoken);
        var albumInfo = albumFacade.findAlbumInfo(albumId, usertoken);
        var response = albumDtoMapper.of(albumInfo);
        return CommonResponse.success(response);
    }

    /**
     * 좋아하는 앨범 리스트
     * @param usertoken
     * @param pageable
     * @return
     */
    @GetMapping("/likeables")
    public CommonResponse retrieveLikeAlbumList(@RequestHeader(value="Authorization", defaultValue = "") String usertoken,
                                                @PageableDefault(size = 100, page = 1) Pageable pageable) {
        System.out.println("LikeApiController :: retrieveLikeTrackList");
        usertoken = TokenGenerator.getHeaderToken(usertoken);
        var albumInfoList = albumFacade.retrieveLikeList(usertoken, pageable);
        var albumInfoDtoList = albumInfoList.stream().map(albumDtoMapper::ofItem).collect(Collectors.toList());
        var response = new AlbumDto.AlbumList(albumInfoDtoList);
        return CommonResponse.success(response);
    }


}
