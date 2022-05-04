package com.muesli.music.infrastructure.artist;

import com.muesli.music.domain.artist.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ArtistRepository  extends JpaRepository<Artist, Long> {

    @Query(value = "SELECT a FROM Artist a " +
            "LEFT JOIN FETCH a.albumList al " +
            "LEFT JOIN FETCH al.likeList " +
            "JOIN FETCH al.trackList t " +
            "LEFT JOIN FETCH t.likeList " +
            "WHERE a.id = :artistId " +
            "GROUP BY al.id")
    Optional<Artist> findArtistById(Long artistId);

    @Query(value = "SELECT a FROM Artist a WHERE a.id = :artistId")
    Optional<Artist> findArtistById2(Long artistId);

    @Query(value = "SELECT a FROM Artist a JOIN FETCH a.likeList l LEFT JOIN FETCH a.bios b WHERE l.userId = :userId")
    Optional<List<Artist>> findAllLikeList(Long userId);

    @Query(value = "SELECT COUNT(a.id) " +
            "FROM artists a JOIN likes l on a.id = l.likeable_id " +
            "WHERE l.user_id = :userId AND l.likeable_type LIKE '%Artist%' AND l.is_like = 1", nativeQuery = true)
    Optional<Integer> countLikeList(Long userId);

    @Query(value = "SELECT a.id, a.name, a.original_name AS originalName, " +
            "a.english_name AS englishName, a.image, a.birthday, a.country, a.debut " +
            "FROM artists a JOIN likes l on a.id = l.likeable_id " +
            "WHERE l.user_id = :userId AND l.likeable_type LIKE '%Artist%' AND l.is_like = 1 " +
            "ORDER BY l.updated_at DESC  " +
            "LIMIT :start, :end", nativeQuery = true)
    Optional<List<Map<String, Object>>> findLikeList(Long userId, int start, int end);

    @Query(value = "SELECT COUNT(a.id) FROM artists a JOIN artist_bios b on a.id = b.artist_id " +
            "WHERE REPLACE(a.name, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '') " +
            "    OR REPLACE(a.original_name, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '') " +
            "    OR REPLACE(a.english_name, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '') " +
            "ORDER BY a.views DESC", nativeQuery = true)
    Optional<Integer> findSearchArtistCount(String keyword);

    @Query(value = "SELECT a.id, a.name, a.original_name AS originalName, " +
            "a.english_name AS englishName, a.image, a.birthday, a.country, a.debut FROM artists a JOIN artist_bios b on a.id = b.artist_id " +
            "WHERE REPLACE(a.name, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '') " +
            "    OR REPLACE(a.original_name, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '') " +
            "    OR REPLACE(a.english_name, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '') " +
            "ORDER BY a.views DESC " +
            "LIMIT :start, :end", nativeQuery = true)
    Optional<List<Map<String, Object>>> findSearchArtistOrderByPopularity(String keyword, int start, int end);

    @Query(value = "SELECT a.id, a.name, a.original_name AS originalName, " +
            "a.english_name AS englishName, a.image, a.birthday, a.country, a.debut FROM artists a JOIN artist_bios b on a.id = b.artist_id " +
            "WHERE REPLACE(a.name, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '') " +
            "    OR REPLACE(a.original_name, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '') " +
            "    OR REPLACE(a.english_name, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '') " +
            "ORDER BY (CASE WHEN ASCII(SUBSTRING(a.name,1)) BETWEEN 48 AND 57 THEN 3 " +
            "              WHEN ASCII(SUBSTRING(a.name,1)) < 128 THEN 2 ELSE 1 END), a.name " +
            "LIMIT :start, :end", nativeQuery = true)
    Optional<List<Map<String, Object>>> findSearchArtistOrderByAlpha(String keyword, int start, int end);



}
