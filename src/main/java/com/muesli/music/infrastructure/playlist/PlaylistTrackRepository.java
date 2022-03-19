package com.muesli.music.infrastructure.playlist;

import com.muesli.music.domain.playlist.track.PlaylistTrack;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaylistTrackRepository extends JpaRepository<PlaylistTrack, Long> {

}
