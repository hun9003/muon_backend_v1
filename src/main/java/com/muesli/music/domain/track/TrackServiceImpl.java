package com.muesli.music.domain.track;

import com.muesli.music.domain.like.LikeInfo;
import com.muesli.music.domain.like.LikeReader;
import com.muesli.music.domain.track.lyrics.LyricsReader;
import com.muesli.music.domain.user.UserInfo;
import com.muesli.music.domain.user.token.UsertokenReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrackServiceImpl implements TrackService{
    private final TrackReader trackReader;
    private final LyricsReader lyricsReader;
    private final LikeReader likeReader;
    private final UsertokenReader usertokenReader;

    /**
     * 트랙 정보 가져오기
     * @param trackId
     * @param userInfo
     * @return
     */
    @Override
    public TrackInfo.Main findTrackInfo(Long trackId, UserInfo.Main userInfo) {
        System.out.println("TrackServiceImpl :: findTrackInfo");
        var track = trackReader.getTrackBy(trackId);
        var lyrics = lyricsReader.getLyricsByTrack(track);
        var trackLikeCount = likeReader.getLikeCount(track.getId(), "App\\Track");
        var trackLikeInfo = new LikeInfo.Main(likeReader.getLikeBy(userInfo.getId(), track.getId(), "App\\Track"), trackLikeCount);

        return new TrackInfo.Main(track, new TrackInfo.LyricsInfo(lyrics), trackLikeInfo);
    }

    /**
     * 좋아요 리스트 조회
     * @param likeableType
     * @param usertoken
     * @return
     */
    @Override
    public List<TrackInfo.Main> getLikeList(String likeableType, String usertoken) {
        System.out.println("LikeServiceImpl :: getLikeTrackList");
        var user = usertokenReader.getUsertoken(usertoken);
        return trackReader.getTrackLikeList(likeableType, user.getUser().getId());
    }

}
