package com.muesli.music.infrastructure.track;

import com.muesli.music.common.exception.EntityNotFoundException;
import com.muesli.music.common.exception.InvalidParamException;
import com.muesli.music.domain.artist.ArtistInfo;
import com.muesli.music.domain.like.LikeInfo;
import com.muesli.music.domain.track.Track;
import com.muesli.music.domain.track.TrackInfo;
import com.muesli.music.domain.track.TrackReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class TrackReaderImpl implements TrackReader {
    private final TrackRepository trackRepository;

    @Override
    public Track getTrackBy(Long trackId) {
        System.out.println("TrackReaderImpl :: getTrackBy");
        return trackRepository.findById(trackId)
                .orElseThrow(InvalidParamException::new);
    }

    @Override
    public List<TrackInfo.Main> getTrackLikeList(String likeableType, Long userId) {
        System.out.println("TrackReaderImpl :: getTrackLikeList");
        var trackList = trackRepository.findAllJoinFetch(likeableType, userId)
                .orElseThrow(EntityNotFoundException::new);
        // 좋아요 갯수 리턴 로직 추가
        return trackList.stream().map(
                track -> {
                    var artistInfo = new ArtistInfo.Main(track.getTrackArtist().getArtist());
                    var trackInfo = new TrackInfo.Main(track, artistInfo);
                    trackInfo.setLikeInfo(new LikeInfo.Main(track.getLikeList().get(0)));
                    return trackInfo;
                }
        ).collect(Collectors.toList());
    }
}
