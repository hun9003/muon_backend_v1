package com.muesli.music.domain.track;

import com.muesli.music.common.exception.BaseException;
import com.muesli.music.common.response.ErrorCode;
import com.muesli.music.domain.artist.ArtistInfo;
import com.muesli.music.domain.like.LikeInfo;
import com.muesli.music.domain.like.LikeReader;
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
        var lyrics = track.getLyrics().iterator().next();
        var trackLikeCount = (long) track.getLikeList().size();
        var trackLikeInfo = new LikeInfo.Main(likeReader.getLikeBy(userInfo.getId(), track.getId(), "App\\Track"), trackLikeCount);

        return new TrackInfo.Main(track, new ArtistInfo.Main(track.getTrackArtists().iterator().next().getArtist()), new TrackInfo.LyricsInfo(lyrics), trackLikeInfo);
    }

    /**
     * 좋아요 리스트 조회
     * @param token
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<TrackInfo.Main> getLikeList(String token) {
        System.out.println("LikeServiceImpl :: getLikeTrackList");
        var usertoken = usertokenReader.getUsertoken(token);
        if(usertoken.getUser() == null) throw new BaseException(ErrorCode.COMMON_BAD_USERTOKEN);
        return trackReader.getTrackLikeList(usertoken.getUser().getId());
    }

}
