package com.muesli.music.application.album;

import com.muesli.music.domain.album.AlbumInfo;
import com.muesli.music.domain.album.AlbumService;
import com.muesli.music.domain.user.token.UsertokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlbumFacade {
    private final AlbumService albumService;
    private final UsertokenService usertokenService;

    /**
     * 앨범 정보 조회
     * @param albumId 앨범 idx
     * @return 앨범 메인 정보
     */
    public AlbumInfo.Main findAlbumInfo(Long albumId, Pageable pageable) {
        System.out.println("AlbumFacade :: findAlbumInfo");
        return albumService.findAlbumInfo(albumId, pageable);
    }

    /**
     * 좋아요 앨범 리스트 조회
     * @param token 유저 토큰
     * @param pageable 앨범 리스트 페이징 처리를 위한 객체
     * @return 앨범 좋아요 정보 리스트
     */
    public List<AlbumInfo.Main> retrieveLikeList(String token, Pageable pageable) {
        System.out.println("AlbumFacade :: retrieveLikeList");
        // 유저 토큰 유효성 검사
        usertokenService.checkUsertoken(token);
        return albumService.getLikeList(token, pageable);
    }

    /**
     * 최신 앨범 리스트 호출
     * @param pageable 앨범 리스트 페이징 처리를 위한 객체
     * @return 최신 앨범 정보 리스트
     */
    public List<AlbumInfo.NewestAlbumInfo> retrieveNewAlbum(Pageable pageable) {
        System.out.println("AlbumFacade :: retrieveNewAlbum");
        return albumService.getNewAlbum(pageable);
    }

}
