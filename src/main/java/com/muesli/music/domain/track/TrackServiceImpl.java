package com.muesli.music.domain.track;

import com.muesli.music.domain.artist.ArtistInfo;
import com.muesli.music.domain.like.LikeInfo;
import com.muesli.music.domain.like.LikeReader;
import com.muesli.music.domain.track.lyrics.LyricsReader;
import com.muesli.music.domain.user.UserInfo;
import com.muesli.music.domain.user.token.UsertokenReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrackServiceImpl implements TrackService{
    private final TrackReader trackReader;
    private final LikeReader likeReader;
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
        var lyrics = track.getLyrics();
        var trackLikeCount = (long) track.getLikeList().size();
        var trackLikeInfo = new LikeInfo.Main(likeReader.getLikeBy(userInfo.getId(), track.getId(), "App\\Track"), trackLikeCount);

        return new TrackInfo.Main(track, new ArtistInfo.Main(track.getTrackArtist().getArtist()), new TrackInfo.LyricsInfo(lyrics), trackLikeInfo);
    }

    /**
     * 좋아요 리스트 조회
     * @param likeableType
     * @param usertoken
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<TrackInfo.Main> getLikeList(String likeableType, String usertoken) {
        System.out.println("LikeServiceImpl :: getLikeTrackList");
        var user = usertokenReader.getUsertoken(usertoken);
        var trackList = trackReader.getTrackLikeList(likeableType, user.getUser().getId());
        return trackList.stream().peek(
                main -> main.getLikeInfo().setLikeCount(likeReader.getLikeCount(main.getId(), likeableType))
        ).collect(Collectors.toList());
    }

}
