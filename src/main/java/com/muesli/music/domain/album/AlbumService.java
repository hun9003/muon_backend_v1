package com.muesli.music.domain.album;

import com.muesli.music.domain.like.LikeInfo;
import com.muesli.music.domain.user.UserInfo;

public interface AlbumService {
    AlbumInfo.Main findAlbumInfo(Long albumId, UserInfo.Main userInfo);

    LikeInfo.Main findLikeBy(UserInfo.Main userInfo, AlbumInfo.Main albumInfo);
}
