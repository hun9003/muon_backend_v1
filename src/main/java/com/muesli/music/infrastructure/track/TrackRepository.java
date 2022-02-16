package com.muesli.music.infrastructure.track;

import com.muesli.music.domain.track.Track;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackRepository  extends JpaRepository<Track, Long> {
}
