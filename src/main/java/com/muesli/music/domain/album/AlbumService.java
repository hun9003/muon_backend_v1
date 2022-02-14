package com.muesli.music.domain.album;

import com.muesli.music.domain.user.UserInfo;

public interface AlbumService {
    AlbumInfo.Main findAlbumInfo(Long albumId, UserInfo.Main userInfo);

    AlbumInfo.LikeInfo findLikeBy(UserInfo.Main userInfo, AlbumInfo.Main albumInfo);
}
