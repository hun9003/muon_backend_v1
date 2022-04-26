package com.muesli.music.interfaces.artist;

import com.muesli.music.application.artist.ArtistFacade;
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
@RequestMapping("/api/v1/artists")
public class ArtistApiController {
    private final ArtistFacade artistFacade;
    private final ArtistDtoMapper artistDtoMapper;

    /**
     * 아티스트 조회
     * @param artistId
     * @return
     */
    @GetMapping("/{id}")
    public CommonResponse retrieveArtist(@PathVariable("id") Long artistId,
                                         @PageableDefault(size = 100, page = 1) Pageable pageable) {
        var artistInfo = artistFacade.findArtistInfo(artistId, pageable);
        var response = artistDtoMapper.of(artistInfo);
        return CommonResponse.success(response);
    }

    /**
     * 좋아하는 아티스트 목록 조회
     * @param usertoken
     * @param pageable
     * @return
     */
    @GetMapping("/likeables")
    public CommonResponse retrieveLikeAlbumList(@RequestHeader(value="Authorization", defaultValue = "") String usertoken,
                                                @PageableDefault(size = 100, page = 1) Pageable pageable) {
        System.out.println("LikeApiController :: retrieveLikeTrackList");
        usertoken = TokenGenerator.getHeaderToken(usertoken);
        var artistInfoList = artistFacade.retrieveLikeList(usertoken, pageable);
        var artistInfoDtoList = artistInfoList.stream().map(artistDtoMapper::ofItem).collect(Collectors.toList());
        var response = new ArtistDto.ArtistList(artistInfoDtoList);
        return CommonResponse.success(response);
    }
}
