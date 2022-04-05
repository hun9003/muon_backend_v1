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
public class TrackServiceImpl implements TrackService{
    private final TrackReader trackReader;
    private final UsertokenReader usertokenReader;

    /**
     * 트랙 정보 가져오기
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
     * @param token
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<TrackInfo.Main> getLikeList(String token, Pageable pageable) {
        System.out.println("LikeServiceImpl :: getLikeTrackList");
        var usertoken = usertokenReader.getUsertoken(token);
        var trackInfoList = trackReader.getTrackLikeList(usertoken.getUser().getId());
        // 페이징
        var pageInfo = new PageInfo(pageable, trackInfoList.size());
        return trackInfoList.subList(pageInfo.getStartNum(), pageInfo.getEndNum());
    }

    /**
     * 곡 순위 리스트
     * @param pageable
     * @param command
     * @return
     */
    public List<TrackInfo.RankInfo> getTrackRank(Pageable pageable, TrackCommand.SearchRankCommand command) {
        System.out.println("LikeServiceImpl :: getLikeTrackList");
        String date = command.getDate() != null ? command.getDate() : String.valueOf(LocalDate.now());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.KOREA);
        LocalDate searchDate = LocalDate.parse(date, formatter);
        LocalDateTime beforeBegin = null;
        LocalDateTime beforeEnd = null;
        LocalDateTime afterBegin = null;
        LocalDateTime afterEnd = null;
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
                beforeBegin = searchDate.minusDays(15).atTime(0, 0);
                beforeEnd = searchDate.minusDays(8).atTime(0, 0);
                afterBegin = searchDate.minusDays(8).atTime(0, 0);
                afterEnd = searchDate.minusDays(1).atTime(0, 0);
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

        var trackList1 = trackReader.getTop100List(beforeBeginFormat, beforeEndFormat, pageable);
        var trackList2 = trackReader.getTop100List(afterBeginFormat, afterEndFormat, pageable);

        var newTrackList = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < trackList2.size(); i++) {
            var newTrackMap = new HashMap<>(trackList2.get(i));
            newTrackMap.put("rank", i+1);
            boolean isNew = true;
            for (int j = 0; j < trackList1.size(); j++) {
                int id = Integer.parseInt(String.valueOf(trackList2.get(i).get("id")));
                int id2 = Integer.parseInt(String.valueOf(trackList1.get(j).get("id")));
                if (id == id2) {
                    newTrackMap.put("wave", j-i);
                    isNew = false;
                    break;
                }
            }
            if(isNew) {
                newTrackMap.put("wave", null);
            }
            newTrackList.add(newTrackMap);
        }

        return newTrackList.stream().map(TrackInfo.RankInfo::new).collect(Collectors.toList());
    }
}
