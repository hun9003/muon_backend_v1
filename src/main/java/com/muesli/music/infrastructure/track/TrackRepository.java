package com.muesli.music.infrastructure.track;

import com.muesli.music.domain.track.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;
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
            "WHERE l.userId = :userId")
    Optional<List<Track>> findAllLikeList(Long userId);

    @Query(value = "SELECT t FROM Track t " +
            "LEFT JOIN FETCH t.trackArtists ta " +
            "LEFT JOIN FETCH ta.artist a " +
            "LEFT JOIN FETCH t.lyrics l " +
            "LEFT JOIN FETCH t.likeList " +
            "LEFT JOIN FETCH a.bios " +
            "WHERE t.album.id = :albumId")
    Optional<List<Track>> findTrackByAlbumId(Long albumId);

    @Query(value = "SELECT count(t.id) AS playCount, (" +
            "    SELECT count(*) FROM likes WHERE likeable_id = t.id AND likeable_type LIKE '%Track%' " +
            "        ) as likeCount, t.* " +
            "FROM play_log p JOIN tracks t ON p.track_id = t.id " +
            "JOIN albums a on t.album_id = a.id " +
            "JOIN artist_track at2 on t.id = at2.track_id " +
            "JOIN artists a2 on at2.artist_id = a2.id " +
            "WHERE p.created_at > '2022-01-01' AND p.created_at < '2022-02-01' " +
            "GROUP BY t.id " +
            "ORDER By count(t.id) DESC, likecount DESC " +
            "LIMIT 100", nativeQuery = true)
    Optional<List<Map<String, Object>>> findTop100();
}
