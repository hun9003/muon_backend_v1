package com.muesli.music.domain.like;

import java.util.List;

public interface LikeService {
    LikeInfo findLikeBy(LikeCommand.RegisterLikeRequest command, String usertoken);

    List<LikeInfo> getLikeList(String likeableType, String usertoken);

    void registerLike(LikeCommand.RegisterLikeRequest command, String usertoken);

    void changeDoLike(LikeCommand.RegisterLikeRequest command, String usertoken);

    void changeDisLike(Long likeId, String usertoken);
}
