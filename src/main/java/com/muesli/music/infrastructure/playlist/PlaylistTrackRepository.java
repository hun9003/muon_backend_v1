package com.muesli.music.infrastructure.playlist;

import com.muesli.music.domain.playlist.track.PlaylistTrack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PlaylistTrackRepository extends JpaRepository<PlaylistTrack, Long> {

    @Query(value = "SELECT * FROM playlist_track WHERE playlist_id = :playlistId AND track_id = :trackId", nativeQuery = true)
    Optional<PlaylistTrack> findPlaylistTrackByPlaylistAndTrack(Long playlistId, Long trackId);

    @Modifying
    @Query(value = "UPDATE playlist_track SET position = position-1 " +
            "WHERE playlist_id = :playlistId " +
            "AND position > :position", nativeQuery = true)
    void changePosition(Long playlistId, int position);
}
