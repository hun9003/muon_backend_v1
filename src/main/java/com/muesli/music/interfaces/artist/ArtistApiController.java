package com.muesli.music.interfaces.artist;

import com.muesli.music.application.artist.ArtistFacade;
import com.muesli.music.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/artists")
public class ArtistApiController {
    private final ArtistFacade artistFacade;
    private final ArtistDtoMapper artistDtoMapper;

    @GetMapping("/{id}")
    public CommonResponse findArtist(@PathVariable("id") Long artistId, @RequestParam(value = "token", defaultValue = "") String usertoken) {
        var albumInfo = artistFacade.findArtistInfo(artistId, usertoken);
        var response = artistDtoMapper.of(albumInfo);
        return CommonResponse.success(response);
    }
}
