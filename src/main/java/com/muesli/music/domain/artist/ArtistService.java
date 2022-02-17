package com.muesli.music.domain.artist;

import com.muesli.music.domain.like.LikeInfo;
import com.muesli.music.domain.user.UserInfo;

public interface ArtistService {
    ArtistInfo.Main findArtistInfo(Long artistId, UserInfo.Main userInfo);

    LikeInfo.Main findLikeBy(UserInfo.Main userInfo, ArtistInfo.Main artistInfo);
}
