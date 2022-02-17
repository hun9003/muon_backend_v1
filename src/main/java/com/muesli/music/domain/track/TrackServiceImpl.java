package com.muesli.music.domain.track;

import com.muesli.music.domain.like.LikeInfo;
import com.muesli.music.domain.like.LikeReader;
import com.muesli.music.domain.track.lyrics.LyricsReader;
import com.muesli.music.domain.user.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TrackServiceImpl implements TrackService{
    private final TrackReader trackReader;
    private final LyricsReader lyricsReader;
    private final LikeReader likeReader;

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
}
