package com.muesli.music.domain.like;

import com.muesli.music.domain.track.TrackInfo;

import java.util.List;

public interface LikeService {
    LikeInfo.Main findLikeBy(LikeCommand.RegisterLikeRequest command, String usertoken);

    List<TrackInfo.Main> getLikeTrackList(String likeableType, String usertoken);

    void registerLike(LikeCommand.RegisterLikeRequest command, String usertoken);

    void changeDoLike(LikeCommand.RegisterLikeRequest command, String usertoken);

    void changeDisLike(Long likeId, String usertoken);
}
