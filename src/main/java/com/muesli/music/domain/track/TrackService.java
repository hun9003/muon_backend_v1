package com.muesli.music.domain.track;

import com.muesli.music.domain.user.UserInfo;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TrackService {
    TrackInfo.Main findTrackInfo(Long trackId, UserInfo.Main userInfo);

    List<TrackInfo.Main> getLikeList(String token, Pageable pageable);

    List<TrackInfo.RankInfo> getTrackRank(Pageable pageable, TrackCommand.SearchRankCommand command);
}
