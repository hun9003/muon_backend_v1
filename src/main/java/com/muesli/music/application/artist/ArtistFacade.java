package com.muesli.music.application.artist;

import com.muesli.music.domain.artist.ArtistInfo;
import com.muesli.music.domain.artist.ArtistService;
import com.muesli.music.domain.user.token.UsertokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArtistFacade {
    private final ArtistService artistService;
    private final UsertokenService usertokenService;

    public ArtistInfo.Main findArtistInfo(Long artistId, String usertoken) {
        var usertokenInfo = usertokenService.findUsertokenInfo(usertoken);
        return artistService.findArtistInfo(artistId, usertokenInfo.getUserInfo());
    }
}
