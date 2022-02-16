package com.muesli.music.infrastructure.track.lyrics;

import com.muesli.music.common.exception.InvalidParamException;
import com.muesli.music.domain.track.Track;
import com.muesli.music.domain.track.lyrics.Lyrics;
import com.muesli.music.domain.track.lyrics.LyricsReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class LyricsReaderImpl implements LyricsReader {
    private final LyricsRepository lyricsRepository;

    @Override
    public Lyrics getLyricsByTrack(Track track) {
        System.out.println("LyricsReaderImpl :: findLyricsByTrack");
        return lyricsRepository.findLyricsByTrack(track)
                .orElse(new Lyrics());
    }
}
