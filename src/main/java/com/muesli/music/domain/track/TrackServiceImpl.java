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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * @param token
     * @param pageable
     * @return
     */
    public List<TrackInfo.Main> getTrackRank(String token, Pageable pageable, String type, String date) {
        System.out.println("LikeServiceImpl :: getLikeTrackList");
        var usertoken = usertokenReader.getUsertoken(token);
        var trackList1 = trackReader.getTop100List("2021-12-01", "2021-12-31");
        var trackList2 = trackReader.getTop100List("2022-01-01", "2022-01-31");
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
        for(var li : newTrackList) {
            System.out.println("---------------------------------------");
            System.out.println("rank : " + li.get("rank") + ",wave : "+ li.get("wave") + ", name : " + li.get("name"));
        }
        return null;
    }
}
