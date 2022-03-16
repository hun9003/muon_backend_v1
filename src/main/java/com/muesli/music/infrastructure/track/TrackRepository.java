package com.muesli.music.infrastructure.track;

import com.muesli.music.domain.track.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface TrackRepository  extends JpaRepository<Track, Long> {

    @Query(value = "SELECT t FROM Track t " +
            "LEFT JOIN FETCH t.likeList l " +
            "LEFT JOIN FETCH t.lyrics " +
            "LEFT JOIN FETCH t.trackArtists ta " +
            "LEFT JOIN FETCH ta.artist a " +
            "LEFT JOIN FETCH a.bios " +
            "WHERE t.id = :trackId")
    Optional<Track> findTrackById(Long trackId);

    @Query(value = "SELECT t FROM Track t JOIN FETCH t.likeList l " +
            "JOIN FETCH t.trackArtists ta " +
            "LEFT JOIN FETCH ta.artist a " +
            "LEFT JOIN FETCH a.bios " +
            "LEFT JOIN FETCH t.lyrics " +
            "WHERE l.likeableType = :likeableType AND l.userId = :userId")
    Optional<List<Track>> findAllJoinFetch(String likeableType, Long userId);

    @Query(value = "SELECT t FROM Track t " +
            "LEFT JOIN FETCH t.trackArtists ta " +
            "LEFT JOIN FETCH ta.artist a " +
            "LEFT JOIN FETCH t.lyrics l " +
            "LEFT JOIN FETCH t.likeList " +
            "LEFT JOIN FETCH a.bios " +
            "WHERE t.album.id = :albumId")
    Optional<List<Track>> findTrackByAlbumId(Long albumId);
}
