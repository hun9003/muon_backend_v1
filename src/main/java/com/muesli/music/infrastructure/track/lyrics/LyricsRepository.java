package com.muesli.music.infrastructure.track.lyrics;

import com.muesli.music.domain.track.Track;
import com.muesli.music.domain.track.lyrics.Lyrics;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LyricsRepository extends JpaRepository<Lyrics, Long> {
    Optional<Lyrics> findLyricsByTrack(Track track);
}
