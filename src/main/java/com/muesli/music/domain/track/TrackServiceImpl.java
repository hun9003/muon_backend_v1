package com.muesli.music.domain.track;

import com.muesli.music.domain.artist.ArtistInfo;
import com.muesli.music.domain.user.UserInfo;
import com.muesli.music.domain.user.token.UsertokenReader;
import com.muesli.music.interfaces.user.PageInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrackServiceImpl implements TrackService {
    private final TrackReader trackReader;
    private final UsertokenReader usertokenReader;

    /**
     * 트랙 정보 가져오기
     *
     * @param trackId
     * @param userInfo
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public TrackInfo.Main findTrackInfo(Long trackId, UserInfo.Main userInfo) {
        System.out.println("TrackServiceImpl :: findTrackInfo");
        var track = trackReader.getTrackBy(trackId);
        var lyrics = track.getLyrics().iterator().next();

        return new TrackInfo.Main(track, new ArtistInfo.Main(track.getTrackArtists().iterator().next().getArtist()), new TrackInfo.LyricsInfo(lyrics));
    }

    /**
     * 좋아요 리스트 조회
     *
     * @param token
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<TrackInfo.Main> getLikeList(String token, Pageable pageable) {
        System.out.println("TrackServiceImpl :: getLikeTrackList");
        var usertoken = usertokenReader.getUsertoken(token);
        var trackInfoList = trackReader.getTrackLikeList(usertoken.getUser().getId());
        // 페이징
        var pageInfo = new PageInfo(pageable, trackInfoList.size());
        return trackInfoList.subList(pageInfo.getStartNum(), pageInfo.getEndNum());
    }

    /**
     * 곡 순위 리스트
     *
     * @param pageable
     * @param command
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<TrackInfo.ChartInfo> getTrackRank(Pageable pageable, TrackCommand.SearchRankCommand command) {
        System.out.println("TrackServiceImpl :: getLikeTrackList");
        String date = command.getDate() != null ? command.getDate() : String.valueOf(LocalDate.now());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.KOREA);
        LocalDate searchDate = LocalDate.parse(date, formatter);
        LocalDateTime beforeBegin;
        LocalDateTime beforeEnd;
        LocalDateTime afterBegin;
        LocalDateTime afterEnd;
        switch (command.getType()) {
            case "day":
                searchDate = LocalDate.now();
                beforeBegin = searchDate.minusDays(2).atTime(0, 0);
                beforeEnd = searchDate.minusDays(1).atTime(0, 0);
                afterBegin = searchDate.minusDays(1).atTime(0, 0);
                afterEnd = searchDate.minusDays(0).atTime(0, 0);
                break;
            case "month":
                beforeBegin = searchDate.minusMonths(1).withDayOfMonth(1).atTime(0, 0);
                beforeEnd = searchDate.minusMonths(0).withDayOfMonth(1).atTime(0, 0);
                afterBegin = searchDate.minusMonths(0).withDayOfMonth(1).atTime(0, 0);
                afterEnd = searchDate.plusMonths(1).withDayOfMonth(1).atTime(0, 0);
                break;
            case "week":
                beforeBegin = searchDate.minusDays(13).atTime(0, 0);
                beforeEnd = searchDate.minusDays(6).atTime(0, 0);
                afterBegin = searchDate.minusDays(6).atTime(0, 0);
                afterEnd = searchDate.plusDays(1).atTime(0, 0);
                break;
            case "now":
            default:
                LocalDateTime now = LocalDateTime.now();
                beforeBegin = now.minusHours(2).minusMinutes(now.getMinute()).minusSeconds(now.getSecond());
                beforeEnd = now.minusHours(1).minusMinutes(now.getMinute()).minusSeconds(now.getSecond());
                afterBegin = now.minusHours(1).minusMinutes(now.getMinute()).minusSeconds(now.getSecond());
                afterEnd = now.minusHours(0).minusMinutes(now.getMinute()).minusSeconds(now.getSecond());
        }
        String beforeBeginFormat = beforeBegin.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String beforeEndFormat = beforeEnd.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String afterBeginFormat = afterBegin.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String afterEndFormat = afterEnd.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        List<Map<String, Object>> trackList1;
        List<Map<String, Object>> trackList2;
        if (command.getGenre() == null) {
            trackList1 = trackReader.getTrackRank(beforeBeginFormat, beforeEndFormat, pageable);
            trackList2 = trackReader.getTrackRank(afterBeginFormat, afterEndFormat, pageable);
        } else {
            trackList1 = trackReader.getTrackGenreRank(beforeBeginFormat, beforeEndFormat, pageable, command.getGenre());
            trackList2 = trackReader.getTrackGenreRank(afterBeginFormat, afterEndFormat, pageable, command.getGenre());
        }

        var newTrackList = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < trackList2.size(); i++) {
            var newTrackMap = new HashMap<>(trackList2.get(i));
            newTrackMap.put("rank", i + 1);
            boolean isNew = true;
            for (int j = 0; j < trackList1.size(); j++) {
                int id = Integer.parseInt(String.valueOf(trackList2.get(i).get("id")));
                int id2 = Integer.parseInt(String.valueOf(trackList1.get(j).get("id")));
                if (id == id2) {
                    newTrackMap.put("wave", j - i);
                    isNew = false;
                    break;
                }
            }
            if (isNew) {
                newTrackMap.put("wave", null);
            }
            newTrackList.add(newTrackMap);
        }

        return newTrackList.stream().map(TrackInfo.ChartInfo::new).collect(Collectors.toList());
    }

    /**
     * 트랙 차트 레이아웃 정보 가져오기
     * @return
     */
    @Override
    public Map<String, Object> getChartLayout() {
        System.out.println("TrackServiceImpl :: getChartLayout");

        LocalDate nowDate = LocalDate.now();
        var chartLayout = new HashMap<String, Object>();

        List<String> yearDate = new ArrayList<>();
        List<String> monthDate = new ArrayList<>();
        List<String> weekDate = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            yearDate.add(String.valueOf(nowDate.minusDays(nowDate.getDayOfMonth()-1).minusMonths(nowDate.getMonthValue()-1).minusYears(i)));
        }
        for (int i = 0; i < nowDate.getMonthValue() + 11; i++) {
            monthDate.add(String.valueOf(nowDate.minusDays(nowDate.getDayOfMonth()-1).minusMonths(i+1)));
        }
        for (int i = 0; i < 12; i++) {
            weekDate.add(String.valueOf(nowDate.minusDays(i*7+1)));
        }

        chartLayout.put("yearDate", yearDate);
        chartLayout.put("monthDate", monthDate);
        chartLayout.put("weekDate", weekDate);

        return chartLayout;
    }

    /**
     * 최신 곡 리스트 호출
     * @param pageable
     * @return
     */
    @Override
    public List<TrackInfo.ChartInfo> getNewTrack(Pageable pageable) {
        System.out.println("TrackServiceImpl :: getChartLayout");
        // 페이징
        var pageInfo = new PageInfo(pageable, 500);
        var trackList = trackReader.getNewTrack(pageInfo.getStartNum(), pageInfo.getEndNum());
        var newTrackList = new ArrayList<Map<String, Object>>();
        for (Map<String, Object> stringObjectMap : trackList) {
            var newTrackMap = new HashMap<>(stringObjectMap);
            newTrackList.add(newTrackMap);
        }
        return newTrackList.stream().map(TrackInfo.ChartInfo::new).collect(Collectors.toList());
    }
}
