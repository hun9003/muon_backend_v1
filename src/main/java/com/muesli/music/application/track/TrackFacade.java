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
     * @param trackId 트랙 idx
     * @return 트랙 정보
     */
    public TrackInfo.Main findTrackInfo(Long trackId) {
        System.out.println("TrackFacade :: findTrackInfo");
        return trackService.findTrackInfo(trackId);
    }


    /**
     * 좋아요 트랙 리스트 조회
     * @param token 유저 토큰
     * @return 트랙 정보 리스트
     */
    public List<TrackInfo.Main> retrieveLikeList(String token, Pageable pageable) {
        System.out.println("TrackFacade :: retrieveLikeList");
        // 유저 토큰 유효성 검사
        usertokenService.checkUsertoken(token);
        return trackService.getLikeList(token, pageable);
    }

    /**
     * 트랙 차트 가져오기
     * @param pageable 트랙 차트 페이징 처리를 위한 데이터 객체
     * @param command 트랙 차트를 불러오기 위한 데이터 객체
     * @return 트랙 정보 리스트
     */
    public List<TrackInfo.ChartInfo> retrieveTrackRank(Pageable pageable, TrackCommand.SearchRankCommand command) {
        System.out.println("TrackFacade :: retrieveTrackRank");
        return trackService.getTrackRank(pageable, command);
    }

    /**
     * 트랙 차트 레이아웃 정보 가져오기
     * @return 레이아웃 정보
     */
    public TrackInfo.ChartLayoutInfo getChartLayout(String type) {
        System.out.println("TrackFacade :: getChartLayout");
        // 레이아웃에 필요한 장르 정보 리스트 호출
        var genreInfoList = genreService.getGenreParentList();
        
        // 레이아웃 정보 호출
        var chartLayout = trackService.getChartLayout();
        
        // 레이아웃 정보에 장르 리스트 저장
        chartLayout.put("genreList", genreInfoList);
        return new TrackInfo.ChartLayoutInfo(chartLayout, type);
    }

    /**
     * 최신 곡 리스트 호출
     * @param pageable 트랙 리스트 페이징 처리를 위한 데이터 객체
     * @return 트랙 정보 리스트
     */
    public List<TrackInfo.NewestTrackInfo> retrieveNewTrack(Pageable pageable) {
        System.out.println("TrackFacade :: retrieveNewTrack");
        return trackService.getNewTrack(pageable);
    }

    /**
     * 유저 최근 들은 곡 리스트 호출
     * @param pageable 트랙 리스트 페이징 처리를 위한 데이터 객체
     * @return 트랙 정보 리스트
     */
    public List<TrackInfo.HistoryTrackInfo> retrieveUserHistoryTrack(String token, Pageable pageable) {
        System.out.println("TrackFacade :: retrieveNewTrack");
        // 유저 토큰을 통해 유저토큰 정보 호출
        var usertokenInfo = usertokenService.findUsertokenInfo(token);
        return trackService.getUserHistoryTrack(usertokenInfo, pageable);
    }

}
