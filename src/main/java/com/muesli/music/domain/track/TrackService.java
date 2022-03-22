package com.muesli.music.domain.track;

import com.muesli.music.domain.user.UserInfo;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TrackService {
    TrackInfo.Main findTrackInfo(Long trackId, UserInfo.Main userInfo);

    List<TrackInfo.Main> getLikeList(String usertoken);

    List<TrackInfo.Main> getTrackRank(String token, Pageable pageable);
}
