package com.muesli.music.infrastructure.playlist;

import com.muesli.music.domain.playlist.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;
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


    @Query(value = "SELECT p FROM Playlist p " +
            "LEFT JOIN FETCH p.likeList l " +
            "LEFT JOIN FETCH p.playlistTrackList pt " +
            "LEFT JOIN FETCH pt.track t " +
            "LEFT JOIN FETCH t.trackArtists ta " +
            "LEFT JOIN FETCH ta.artist ar " +
            "LEFT JOIN FETCH t.album a " +
            "WHERE p.userId = :userId " +
            "GROUP BY p.id")
    Optional<List<Playlist>> findPlaylistByUserId(Long userId);

    @Query(value = "SELECT COUNT(id) FROM (" +
            "SELECT p.id FROM playlists p " +
            "JOIN `users` u on p.user_id = u.id " +
            "WHERE u.id = :userId ) playlist", nativeQuery = true)
    Optional<Integer> countPlaylistByUserId(Long userId);

    @Query(value = "SELECT p.id, p.name, p.image, p.views, p.description, p.created_at AS createAt, p.public AS isPublic ," +
            "u.id AS userId, u.username AS userName, count(pt.id) AS trackCount  FROM playlists p " +
            "JOIN `users` u on p.user_id = u.id " +
            "LEFT JOIN playlist_track pt on p.id = pt.playlist_id " +
            "WHERE u.id = :userId " +
            "GROUP BY p.id " +
            "ORDER BY p.created_at DESC " +
            "LIMIT :start, :end ", nativeQuery = true)
    Optional<List<Map<String, Object>>> findPlaylistByUserId(Long userId, int start, int end);


    @Query(value = "SELECT p FROM Playlist p " +
            "LEFT JOIN FETCH p.likeList l " +
            "LEFT JOIN FETCH p.playlistTrackList pt " +
            "LEFT JOIN FETCH pt.track t " +
            "LEFT JOIN FETCH t.trackArtists ta " +
            "LEFT JOIN FETCH ta.artist ar " +
            "LEFT JOIN FETCH t.album a " +
            "WHERE l.userId = :userId " +
            "GROUP BY p.id")
    Optional<List<Playlist>> findAllLikeList(Long userId);

    @Query(value = "SELECT COUNT(id) FROM (" +
            "SELECT p.id FROM playlists p " +
            "JOIN `users` u on p.user_id = u.id " +
            "JOIN playlist_track pt on p.id = pt.playlist_id " +
            "JOIN likes l on l.likeable_id = p.id " +
            "WHERE l.user_id = :userId AND likeable_type LIKE '%Playlist%' AND l.is_like = 1 AND (l.user_id = :userId OR p.public = 1) " +
            "GROUP BY p.id ) AS playlist", nativeQuery = true)
    Optional<Integer> countLikeList(Long userId);

    @Query(value = "SELECT p.id, p.name, p.image, p.views, p.description, p.created_at AS createAt, p.public AS isPublic ," +
            "u.id AS userId, u.username AS userName, count(pt.id) AS trackCount FROM playlists p " +
            "JOIN `users` u on p.user_id = u.id " +
            "JOIN playlist_track pt on p.id = pt.playlist_id " +
            "JOIN likes l on l.likeable_id = p.id " +
            "WHERE l.user_id = :userId AND likeable_type LIKE '%Playlist%' AND l.is_like = 1 AND (l.user_id = :userId OR p.public = 1) " +
            "GROUP BY p.id " +
            "ORDER BY p.created_at DESC " +
            "LIMIT :start, :end ", nativeQuery = true)
    Optional<List<Map<String, Object>>> findLikeList(Long userId, int start, int end);



}
