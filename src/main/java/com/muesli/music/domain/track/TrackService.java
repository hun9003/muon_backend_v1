package com.muesli.music.domain.track;

import com.muesli.music.domain.user.UserInfo;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface TrackService {
    TrackInfo.Main findTrackInfo(Long trackId, UserInfo.Main userInfo);

    List<TrackInfo.Main> getLikeList(String token, Pageable pageable);

    List<TrackInfo.ChartInfo> getTrackRank(Pageable pageable, TrackCommand.SearchRankCommand command);

    Map<String, Object> getChartLayout();

    List<TrackInfo.ChartInfo> getNewTrack(Pageable pageable);

    List<TrackInfo.ChartInfo> getUserHistoryTrack(UserInfo.UsertokenInfo userInfo, Pageable pageable);
}
