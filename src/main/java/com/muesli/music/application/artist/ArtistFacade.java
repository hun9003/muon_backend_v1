package com.muesli.music.application.artist;

import com.muesli.music.domain.artist.ArtistInfo;
import com.muesli.music.domain.artist.ArtistService;
import com.muesli.music.domain.user.token.UsertokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
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
     * @param artistId 아티스트 idx
     * @param pageable 아티스트의 앨범과 트랙 페이징 처리를 위한 객체
     * @return 아티스트 메인 정보
     */
    public ArtistInfo.Main findArtistInfo2(Long artistId, Pageable pageable) {
        System.out.println("ArtistFacade :: findArtistInfo");
        return artistService.findArtistInfo(artistId, pageable);
    }

    /**
     * 좋아하는 아티스트 리스트 조회
     * @param token 유저 토큰
     * @return 아티스트 정보 리스트
     */
    public List<ArtistInfo.ArtistListInfo> retrieveLikeList2(String token, Pageable pageable) {
        System.out.println("ArtistFacade :: retrieveLikeList");
        // 유저 토큰 유효성 검사
        usertokenService.checkUsertoken(token);
        return artistService.getLikeList(token, pageable);
    }
}
