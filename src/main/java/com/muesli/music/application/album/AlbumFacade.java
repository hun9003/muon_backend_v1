package com.muesli.music.application.album;

import com.muesli.music.domain.album.AlbumInfo;
import com.muesli.music.domain.album.AlbumService;
import com.muesli.music.domain.like.LikeInfo;
import com.muesli.music.domain.user.UserService;
import com.muesli.music.domain.user.token.UsertokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlbumFacade {
    private final AlbumService albumService;
    private final UsertokenService usertokenService;

    public AlbumInfo.Main findAlbumInfo(Long albumId, String usertoken) {
        System.out.println("AlbumFacade :: findAlbumInfo");
        var usertokenInfo = usertokenService.findUsertokenInfo(usertoken);
        return albumService.findAlbumInfo(albumId, usertokenInfo.getUserInfo());
    }

}
