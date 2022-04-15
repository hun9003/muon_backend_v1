package com.muesli.music.domain.track.lyrics;

import com.muesli.music.domain.track.Track;

import java.util.List;
import java.util.Map;

public interface LyricsReader {
    Lyrics getLyricsByTrack(Track track);

    int getSearchLyricsCount(String keyword);

    List<Map<String, Object>> getSearchLyrics(String keyword, String type, int start, int end);
}
