package com.muesli.music.application.track;

import com.muesli.music.domain.genre.GenreService;
import com.muesli.music.domain.track.TrackCommand;
import com.muesli.music.domain.track.TrackInfo;
import com.muesli.music.domain.track.TrackService;
import com.muesli.music.domain.user.token.UsertokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrackFacade {
    private final TrackService trackService;
    private final GenreService genreService;
    private final UsertokenService usertokenService;

    /**
     * 트랙 정보 조회
     * @param trackId
     * @param token
     * @return
     */
    public TrackInfo.Main findTrackInfo(Long trackId, String token) {
        System.out.println("TrackFacade :: findTrackInfo");
        // 유저 토큰 조회
        var usertokenInfo = usertokenService.findUsertokenInfo(token);
        // 트랙 조회
        return trackService.findTrackInfo(trackId, usertokenInfo.getUserInfo());
    }


    /**
     * 좋아요 트랙 리스트 조회
     * @param token
     * @return
     */
    public List<TrackInfo.Main> retrieveLikeList(String token, Pageable pageable) {
        System.out.println("TrackFacade :: retrieveLikeList");
        usertokenService.checkUsertoken(token);
        return trackService.getLikeList(token, pageable);
    }

    /**
     * 곡 정보 조회
     * @param pageable
     * @param command
     * @return
     */
    public List<TrackInfo.ChartInfo> retrieveTrackRank(Pageable pageable, TrackCommand.SearchRankCommand command) {
        System.out.println("TrackFacade :: retrieveTop100");
        return trackService.getTrackRank(pageable, command);
    }

    /**
     * 트랙 차트 레이아웃 정보 가져오기
     * @return
     */
    public TrackInfo.ChartLayoutInfo getChartLayout() {
        System.out.println("TrackFacade :: getChartLayout");
        var genreInfoList = genreService.getGenreList();
        var chartLayout = trackService.getChartLayout();
        chartLayout.put("genreList", genreInfoList);
        return new TrackInfo.ChartLayoutInfo(chartLayout);
    }

    /**
     * 최신 곡 리스트 호출
     * @param pageable
     * @return
     */
    public List<TrackInfo.ChartInfo> retrieveNewTrack(Pageable pageable) {
        System.out.println("TrackFacade :: retrieveNewTrack");
        return trackService.getNewTrack(pageable);
    }

    /**
     * 유저 최근 들은 곡 리스트 호출
     * @param pageable
     * @return
     */
    public List<TrackInfo.ChartInfo> retrieveUserHistoryTrack(String token, Pageable pageable) {
        System.out.println("TrackFacade :: retrieveNewTrack");
        var usertokenInfo = usertokenService.findUsertokenInfo(token);
        return trackService.getUserHistoryTrack(usertokenInfo, pageable);
    }
}
