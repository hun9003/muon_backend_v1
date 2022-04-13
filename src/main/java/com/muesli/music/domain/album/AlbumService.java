package com.muesli.music.domain.album;

import com.muesli.music.domain.user.UserInfo;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AlbumService {
    AlbumInfo.Main findAlbumInfo(Long albumId, UserInfo.Main userInfo);

    List<AlbumInfo.Main> getLikeList(String usertoken, Pageable pageable);

    List<AlbumInfo.NewestAlbumInfo> getNewAlbum(Pageable pageable);
}
