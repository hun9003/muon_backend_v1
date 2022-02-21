package com.muesli.music.domain.artist;

import com.muesli.music.domain.like.LikeInfo;
import com.muesli.music.domain.user.UserInfo;

import java.util.List;

public interface ArtistService {
    ArtistInfo.Main findArtistInfo(Long artistId, UserInfo.Main userInfo);

    LikeInfo.Main findLikeBy(UserInfo.Main userInfo, ArtistInfo.Main artistInfo);

    List<ArtistInfo.Main> getLikeList(String likeableType, String usertoken);
}
