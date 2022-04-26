package com.muesli.music.infrastructure.track.lyrics;

import com.muesli.music.common.util.Constant;
import com.muesli.music.domain.track.Track;
import com.muesli.music.domain.track.lyrics.Lyrics;
import com.muesli.music.domain.track.lyrics.LyricsReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @Override
    public int getSearchLyricsCount(String keyword) {
        System.out.println("LyricsReaderImpl :: getSearchLyrics");
        return lyricsRepository.findSearchLyricsCount(keyword).orElse(0);
    }

    @Override
    public List<Map<String, Object>> getSearchLyrics(String keyword, String type, int start, int end) {
        System.out.println("LyricsReaderImpl :: getSearchLyrics");
        List<Map<String, Object>> lyricsList;
        switch (type) {
            case Constant.Order.ALPHA : lyricsList = lyricsRepository.findSearchLyricsOrderByAlpha(keyword, start, end).orElse(new ArrayList<>()); break;
            case Constant.Order.NEWEST : lyricsList = lyricsRepository.findSearchLyricsOrderByNewest(keyword, start, end).orElse(new ArrayList<>()); break;
            case Constant.Order.SIMILAR:
            default: lyricsList = lyricsRepository.findSearchLyricsOrderBySimilar(keyword, start, end).orElse(new ArrayList<>());
        }
        return lyricsList;
    }


}
