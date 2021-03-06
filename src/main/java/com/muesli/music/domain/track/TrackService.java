package com.muesli.music.domain.track;

import com.muesli.music.domain.search.SearchCommand;
import com.muesli.music.domain.user.UserInfo;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface TrackService {
    TrackInfo.Main findTrackInfo(Long trackId);

    List<TrackInfo.TrackListInfo> getLikeList(String token, Pageable pageable);

    List<TrackInfo.ChartInfo> getTrackRank(Pageable pageable, TrackCommand.SearchRankCommand command);

    Map<String, Object> getChartLayout();

    List<TrackInfo.TrackListInfo> getNewTrack(Pageable pageable);

    List<TrackInfo.TrackListInfo> getUserHistoryTrack(UserInfo.UsertokenInfo userInfo, Pageable pageable);

    int getSearchTrackCount(SearchCommand.SearchRequest command);

    List<TrackInfo.TrackListInfo> getSearchTrack(SearchCommand.SearchRequest command, Pageable pageable);

    int getSearchLyricsCount(SearchCommand.SearchRequest command);

    List<TrackInfo.SearchLyricsInfo> getSearchLyrics(SearchCommand.SearchRequest command, Pageable pageable);

    List<TrackInfo.TrackListInfo> getGenreTrackList(Long genreId, String type, Pageable pageable);
}
