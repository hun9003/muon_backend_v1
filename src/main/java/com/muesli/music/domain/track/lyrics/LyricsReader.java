package com.muesli.music.domain.track.lyrics;

import com.muesli.music.domain.track.Track;

public interface LyricsReader {
    Lyrics findLyricsByTrack(Track track);
}
