package com.muesli.music.infrastructure.playlist;

import com.muesli.music.domain.playlist.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
}
