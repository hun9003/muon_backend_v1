package com.muesli.music.domain.track;

import com.muesli.music.domain.user.UserInfo;

import java.util.List;

public interface TrackService {
    TrackInfo.Main findTrackInfo(Long trackId, UserInfo.Main userInfo);

    List<TrackInfo.Main> getLikeList(String likeableType, String usertoken);
}
