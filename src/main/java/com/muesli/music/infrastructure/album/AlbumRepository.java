package com.muesli.music.infrastructure.album;

import com.muesli.music.domain.album.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface AlbumRepository extends JpaRepository<Album, Long> {

    /**
     * 앨범 pk로 조회
     * @param albumId
     * @return
     */
    @Query(value = "SELECT a FROM Album a " +
            "LEFT JOIN FETCH a.likeList " +
            "LEFT JOIN FETCH a.trackList t " +
            "LEFT JOIN FETCH t.lyrics " +
            "LEFT JOIN FETCH t.trackArtists ta " +
            "LEFT JOIN FETCH t.likeList " +
            "LEFT JOIN FETCH ta.artist ar " +
            "LEFT JOIN FETCH ar.bios " +
            "WHERE a.id = :albumId")
    Optional<Album> findAlbumById(Long albumId);

    /**
     * 좋아하는 앨범 리스트 조회
     * @param userId
     * @return
     */
    @Query(value = "SELECT a FROM Album a JOIN FETCH a.likeList l WHERE l.userId = :userId")
    Optional<List<Album>> findAllLikeList(Long userId);

    /**
     * 최신 앨범 조회
     * @param start
     * @param end
     * @return
     */
    @Query(value = "SELECT a.id, a.name, a.image, a.original_name AS originalName, a.release_date AS releaseDate, " +
            "a2.id AS artistId, a2.name AS artistName " +
            "FROM albums a " +
            "JOIN tracks t on t.album_id = a.id " +
            "JOIN artist_track at on at.track_id = t.id " +
            "JOIN artists a2 on a2.id = at.artist_id " +
            "GROUP BY a.id " +
            "ORDER By a.release_date DESC " +
            "LIMIT :start, :end", nativeQuery = true)
    Optional<List<Map<String, Object>>> findNewAlbum(int start, int end);

    /**
     * 앨범 검색 키워드로 조회 (개수)
     * @param keyword
     * @return
     */
    @Query(value = "SELECT COUNT(id) FROM (" +
            "SELECT a.id as id " +
            "FROM albums a LEFT JOIN play_log p ON p.album_id = a.id " +
            "LEFT JOIN tracks t on t.album_id = a.id " +
            "LEFT JOIN artist_track at on at.track_id = t.id " +
            "LEFT JOIN artists a2 on a2.id = at.artist_id " +
            "WHERE REPLACE(a.name, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '') " +
            "OR REPLACE(t.name, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '') " +
            "OR REPLACE(a2.name, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '') " +
            "OR REPLACE(a.description, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '') " +
            "GROUP BY a.id) as album", nativeQuery = true)
    Optional<Integer> findSearchAlbumCount(String keyword);


    /**
     * 앨범 검색 키워드로 조회 (인기순)
     * @param keyword
     * @param start
     * @param end
     * @return
     */
    @Query(value = "SELECT a.id, a.name, a.image, a.original_name AS originalName, a.release_date AS releaseDate, " +
            "a2.id AS artistId, a2.name AS artistName " +
            "FROM albums a LEFT JOIN play_log p ON p.album_id = a.id " +
            "LEFT JOIN tracks t on t.album_id = a.id " +
            "LEFT JOIN artist_track at on at.track_id = t.id " +
            "LEFT JOIN artists a2 on a2.id = at.artist_id " +
            "WHERE REPLACE(a.name, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '') " +
            "OR REPLACE(t.name, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '') " +
            "OR REPLACE(a2.name, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '') " +
            "OR REPLACE(a.description, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '') " +
            "GROUP BY a.id " +
            "ORDER By COUNT(p.idx) DESC " +
            "LIMIT :start, :end", nativeQuery = true)
    Optional<List<Map<String, Object>>> findSearchAlbumOrderByPopularity(String keyword, int start, int end);

    /**
     * 앨범 검색 키워드로 조회 (관련도순)
     * @param keyword
     * @param start
     * @param end
     * @return
     */
    @Query(value = "SELECT a.id, a.name, a.image, a.original_name AS originalName, a.release_date AS releaseDate, " +
            "a2.id AS artistId, a2.name AS artistName " +
            "FROM albums a LEFT JOIN play_log p ON p.album_id = a.id " +
            "LEFT JOIN tracks t on t.album_id = a.id " +
            "LEFT JOIN artist_track at on at.track_id = t.id " +
            "LEFT JOIN artists a2 on a2.id = at.artist_id " +
            "WHERE REPLACE(a.name, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '') " +
            "OR REPLACE(t.name, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '') " +
            "OR REPLACE(a2.name, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '') " +
            "OR REPLACE(a.description, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '') " +
            "GROUP BY a.id " +
            "ORDER By " +
            "(CASE WHEN (REPLACE(a.name, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '')) AND REPLACE(t.name, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '') THEN 1 " +
            "          WHEN REPLACE(a.name, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '') THEN 2 " +
            "          WHEN REPLACE(t.name, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '') THEN 3 " +
            "          WHEN REPLACE(a2.name, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '') THEN 4 " +
            "          ELSE 5 END) " +
            "LIMIT :start, :end", nativeQuery = true)
    Optional<List<Map<String, Object>>> findSearchAlbumOrderBySimilar(String keyword, int start, int end);

    /**
     * 앨범 검색 키워드로 조회 (최신순)
     * @param keyword
     * @param start
     * @param end
     * @return
     */
    @Query(value = "SELECT a.id, a.name, a.image, a.original_name AS originalName, a.release_date AS releaseDate, " +
            "a2.id AS artistId, a2.name AS artistName " +
            "FROM albums a LEFT JOIN play_log p ON p.album_id = a.id " +
            "LEFT JOIN tracks t on t.album_id = a.id " +
            "LEFT JOIN artist_track at on at.track_id = t.id " +
            "LEFT JOIN artists a2 on a2.id = at.artist_id " +
            "WHERE REPLACE(a.name, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '') " +
            "OR REPLACE(t.name, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '') " +
            "OR REPLACE(a2.name, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '') " +
            "OR REPLACE(a.description, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '') " +
            "GROUP BY a.id " +
            "ORDER By a.release_date DESC " +
            "LIMIT :start, :end", nativeQuery = true)
    Optional<List<Map<String, Object>>> findSearchAlbumOrderByNewest(String keyword, int start, int end);
}
