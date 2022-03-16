package com.muesli.music.infrastructure.playlist;

import com.muesli.music.domain.playlist.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {

    @Query(value = "SELECT p FROM Playlist p " +
            "LEFT JOIN FETCH p.likeList " +
            "LEFT JOIN FETCH p.playlistTrackList ptl " +
            "LEFT JOIN FETCH ptl.track t " +
            "LEFT JOIN FETCH t.likeList " +
            "LEFT JOIN FETCH t.trackArtist ta " +
            "LEFT JOIN FETCH ta.artist " +
            "LEFT JOIN FETCH t.album a " +
            "WHERE p.id = :playlistId")
    Optional<Playlist> findPlaylistById(Long playlistId);
}
