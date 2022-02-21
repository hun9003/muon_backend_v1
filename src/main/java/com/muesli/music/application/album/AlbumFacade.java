package com.muesli.music.application.album;

import com.muesli.music.domain.album.AlbumInfo;
import com.muesli.music.domain.album.AlbumService;
import com.muesli.music.domain.like.LikeInfo;
import com.muesli.music.domain.user.UserService;
import com.muesli.music.domain.user.token.UsertokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
     * @param usertoken
     * @return
     */
    public AlbumInfo.Main findAlbumInfo(Long albumId, String usertoken) {
        System.out.println("AlbumFacade :: findAlbumInfo");
        var usertokenInfo = usertokenService.findUsertokenInfo(usertoken);
        return albumService.findAlbumInfo(albumId, usertokenInfo.getUserInfo());
    }

    /**
     * 좋아요 앨범 리스트 조회
     * @param usertoken
     * @return
     */
    public List<AlbumInfo.Main> retrieveLikeList(String usertoken) {
        System.out.println("TrackFacade :: retrieveLikeList");
        return albumService.getLikeList("App\\Album", usertoken);
    }

}
