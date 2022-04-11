package com.muesli.music.domain.like;


import java.util.List;

public interface LikeService {
    LikeInfo.Main findLikeBy(LikeCommand.RegisterLikeRequest command, String usertoken);

    void registerLike(LikeCommand.RegisterLikeRequest command, String usertoken);

    void changeDoLike(Long likeId, String token);

    void changeDisLike(Long likeId, String usertoken);

    List<LikeInfo.Main> findLikeBy(LikeCommand.ShowLikeListRequest command, String token);

    LikeInfo.Main getLike(Long likeId);
}
