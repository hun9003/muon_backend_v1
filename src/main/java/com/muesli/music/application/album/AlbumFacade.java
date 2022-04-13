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
     * @param albumId
     * @param token
     * @return
     */
    public AlbumInfo.Main findAlbumInfo(Long albumId, String token) {
        System.out.println("AlbumFacade :: findAlbumInfo");
        var usertokenInfo = usertokenService.findUsertokenInfo(token);
        return albumService.findAlbumInfo(albumId, usertokenInfo.getUserInfo());
    }

    /**
     * 좋아요 앨범 리스트 조회
     * @param token
     * @return
     */
    public List<AlbumInfo.Main> retrieveLikeList(String token, Pageable pageable) {
        System.out.println("AlbumFacade :: retrieveLikeList");
        usertokenService.checkUsertoken(token);
        return albumService.getLikeList(token, pageable);
    }

    /**
     * 최신 앨범 리스트 호출
     * @param pageable
     * @return
     */
    public List<AlbumInfo.NewestAlbumInfo> retrieveNewAlbum(Pageable pageable) {
        System.out.println("AlbumFacade :: retrieveNewAlbum");
        return albumService.getNewAlbum(pageable);
    }
}
