package com.muesli.music.application.artist;

import com.muesli.music.domain.artist.ArtistInfo;
import com.muesli.music.domain.artist.ArtistService;
import com.muesli.music.domain.user.token.UsertokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArtistFacade {
    private final ArtistService artistService;
    private final UsertokenService usertokenService;

    /**
     * 아티스트 정보 조회
     * @param artistId
     * @param usertoken
     * @return
     */
    public ArtistInfo.Main findArtistInfo(Long artistId, String usertoken) {
        System.out.println("ArtistFacade :: findArtistInfo");
        var usertokenInfo = usertokenService.findUsertokenInfo(usertoken);
        return artistService.findArtistInfo(artistId, usertokenInfo.getUserInfo());
    }

    public List<ArtistInfo.Main> retrieveLikeList(String usertoken) {
        System.out.println("ArtistFacade :: retrieveLikeList");
        return artistService.getLikeList("App\\Artist", usertoken);
    }
}