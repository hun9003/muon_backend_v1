package com.muesli.music.domain.like;


import java.util.List;

public interface LikeService {
    LikeInfo.Main findLikeBy(LikeCommand.RegisterLikeRequest command, String usertoken);

    LikeInfo.Main registerLike(LikeCommand.RegisterLikeRequest command, String usertoken);

    LikeInfo.Main changeDoLike(Long likeId, String token);

    LikeInfo.Main changeDisLike(Long likeId, String usertoken);

    List<LikeInfo.Main> findLikeBy(LikeCommand.ShowLikeListRequest command, String token);

    LikeInfo.Main getLike(Long likeId);
}
