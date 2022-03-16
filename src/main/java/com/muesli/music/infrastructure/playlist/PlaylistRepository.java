package com.muesli.music.infrastructure.playlist;

import com.muesli.music.domain.playlist.Playlist;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.web.PageableDefault;

import java.util.Optional;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {

    @Query(value = "SELECT distinct p FROM Playlist p " +
            "LEFT JOIN FETCH p.likeList " +
            "LEFT JOIN FETCH p.playlistTrackList ptl " +
            "LEFT JOIN FETCH ptl.track t " +
            "LEFT JOIN FETCH t.likeList tl " +
            "LEFT JOIN FETCH t.trackArtists ta " +
            "LEFT JOIN FETCH ta.artist ar " +
            "LEFT JOIN FETCH t.album a " +
            "LEFT JOIN FETCH t.lyrics " +
            "LEFT JOIN FETCH ar.bios " +
            "WHERE p.id = :playlistId " +
            "GROUP BY ptl.track " +
            "ORDER BY ptl.position ASC")
    Optional<Playlist> findPlaylistById(Long playlistId);


}
