package com.muesli.music.infrastructure.track;

import com.muesli.music.common.exception.InvalidParamException;
import com.muesli.music.domain.track.Track;
import com.muesli.music.domain.track.TrackReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

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
}
