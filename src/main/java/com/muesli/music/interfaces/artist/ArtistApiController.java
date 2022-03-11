package com.muesli.music.interfaces.artist;

import com.muesli.music.application.artist.ArtistFacade;
import com.muesli.music.common.response.CommonResponse;
import com.muesli.music.common.util.TokenGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/artists")
public class ArtistApiController {
    private final ArtistFacade artistFacade;
    private final ArtistDtoMapper artistDtoMapper;

    @GetMapping("/{id}")
    public CommonResponse retrieveArtist(@PathVariable("id") Long artistId, @RequestHeader(value="Authorization", defaultValue = "") String usertoken) {
        usertoken = TokenGenerator.getHeaderToken(usertoken);
        var albumInfo = artistFacade.findArtistInfo(artistId, usertoken);
        var response = artistDtoMapper.of(albumInfo);
        return CommonResponse.success(response);
    }

    @GetMapping("/likeables")
    public CommonResponse retrieveLikeAlbumList(@RequestHeader(value="Authorization", defaultValue = "") String usertoken) {
        System.out.println("LikeApiController :: retrieveLikeTrackList");
        usertoken = TokenGenerator.getHeaderToken(usertoken);
        var artistInfoList = artistFacade.retrieveLikeList(usertoken);
        var artistInfoDtoList = artistInfoList.stream().map(artistDtoMapper::ofItem).collect(Collectors.toList());
        var response = new ArtistDto.ArtistList(artistInfoDtoList);
        return CommonResponse.success(response);
    }
}