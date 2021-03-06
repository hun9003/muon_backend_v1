package com.muesli.music.infrastructure.album;

import com.muesli.music.domain.album.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface AlbumRepository extends JpaRepository<Album, Long> {

    /**
     * 앨범 idx로 조회
     * @param albumId
     * @return
     */
    @Query(value = "SELECT a FROM Album a WHERE a.id = :albumId")
    Optional<Album> findAlbumById(Long albumId);

    /**
     * 아티스트 idx로 앨범 개수 조회
     * @param artistId
     * @return
     */
    Optional<Integer> countAlbumByArtistId(Long artistId);

    /**
     * 아티스트 idx로 조회
     * @param artistId
     * @param start
     * @param end
     * @return
     */
    @Query(value = "SELECT a.id, a.name, a.image, a.original_name AS originalName, a.release_date AS releaseDate, " +
            "            a2.id AS artistId, a2.name AS artistName " +
            "            FROM albums a " +
            "            JOIN artists a2 on a2.id = a.artist_id " +
            "            WHERE a.artist_id = :artistId " +
            "            GROUP BY a.id " +
            "            ORDER By a.id DESC " +
            "            LIMIT :start, :end", nativeQuery = true)
    Optional<List<Map<String, Object>>> findAlbumByArtist(Long artistId, int start, int end);

    /**
     * 좋아하는 앨범 리스트 개수
     * @param userId
     * @return
     */
    @Query(value = "SELECT COUNT(id) FROM (" +
            "            SELECT a.id FROM albums a " +
            "            JOIN artists a2 on a2.id = a.artist_id " +
            "            JOIN likes l on l.likeable_id = a.id " +
            "            WHERE l.user_id = :userId AND l.likeable_type LIKE '%Album%' AND is_like = 1 " +
            "            GROUP BY a.id ) AS album", nativeQuery = true)
    Optional<Integer> countLikeList(Long userId);

    /**
     * 좋아하는 앨범 리스트 호출
     * @param userId
     * @param start
     * @param end
     * @return
     */
    @Query(value = "SELECT a.id, a.name, a.image, a.original_name AS originalName, a.release_date AS releaseDate, " +
            "            a2.id AS artistId, a2.name AS artistName " +
            "            FROM albums a " +
            "            JOIN artists a2 on a2.id = a.artist_id " +
            "            JOIN likes l on l.likeable_id = a.id " +
            "            WHERE l.user_id = :userId AND l.likeable_type LIKE '%Album%' AND is_like = 1 " +
            "            GROUP BY a.id " +
            "            ORDER BY l.updated_at DESC " +
            "            LIMIT :start, :end", nativeQuery = true)
    Optional<List<Map<String, Object>>> findLikeList(Long userId, int start, int end);

    /**
     * 최신 앨범 조회
     * @param start
     * @param end
     * @return
     */
    @Query(value = "SELECT a.id, a.name, a.image, a.original_name AS originalName, a.release_date AS releaseDate, " +
            "a2.id AS artistId, a2.name AS artistName " +
            "FROM albums a " +
            "JOIN artists a2 on a2.id = a.artist_id " +
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
            "FROM albums a " +
            "JOIN tracks t ON a.id = t.album_id " +
            "JOIN artists a2 on a2.id = a.artist_id " +
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
            "JOIN tracks t ON a.id = t.album_id " +
            "JOIN artists a2 on a2.id = a.artist_id " +
            "WHERE REPLACE(a.name, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '') " +
            "OR REPLACE(t.name, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '') " +
            "OR REPLACE(a2.name, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '') " +
            "OR REPLACE(a.description, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '') " +
            "GROUP BY a.id " +
            "ORDER By COUNT(p.id) DESC " +
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
            "JOIN tracks t ON a.id = t.album_id " +
            "JOIN artists a2 on a2.id = a.artist_id " +
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
            "JOIN tracks t ON a.id = t.album_id " +
            "JOIN artists a2 on a2.id = a.artist_id " +
            "WHERE REPLACE(a.name, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '') " +
            "OR REPLACE(t.name, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '') " +
            "OR REPLACE(a2.name, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '') " +
            "OR REPLACE(a.description, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '') " +
            "GROUP BY a.id " +
            "ORDER By a.release_date DESC " +
            "LIMIT :start, :end", nativeQuery = true)
    Optional<List<Map<String, Object>>> findSearchAlbumOrderByNewest(String keyword, int start, int end);

    /**
     * 장르별 앨범 리스트 호출
     * @param genreId
     * @param limit
     * @return
     */
    @Query(value = "SELECT a.id, a.name, a.image, a.original_name AS originalName, a.release_date AS releaseDate, a2.id AS artistId, a2.name AS artistName " +
            "    FROM genres g " +
            "    JOIN genreables g2 on g.id = g2.genre_id " +
            "    JOIN tracks t ON t.id = g2.genreable_id " +
            "    JOIN albums a on a.id = t.album_id " +
            "    JOIN artists a2 on a.artist_id = a2.id " +
            "WHERE g.id = :genreId " +
            "GROUP BY a.id ORDER BY g.id DESC " +
            "LIMIT :limit", nativeQuery = true)
    Optional<List<Map<String, Object>>> findGenreAlbumList(Long genreId, int limit);

    /**
     * 채널별 앨범 리스트 개수
     * @param channelId 채널 id
     * @return
     */
    @Query(value = "SELECT COUNT(id) FROM (" +
            "SELECT a.id " +
            "FROM channels c LEFT JOIN channelables ca ON ca.channel_id = c.id " +
            "JOIN albums a on ca.channelable_id = a.id " +
            "JOIN artists a2 on a2.id = a.artist_id " +
            "WHERE ca.channelable_type LIKE '%Album%' AND c.id = :channelId " +
            "GROUP BY a.id " +
            "ORDER By a.id ) as album", nativeQuery = true)
    Optional<Integer> countChannelAlbum(Long channelId);

    /**
     * 채널별 앨범 리스트
     * @param channelId
     * @param start
     * @param end
     * @return
     */
    @Query(value = "SELECT a.id, a.name, a.image, a.original_name AS originalName, a.release_date AS releaseDate, " +
            "a2.id AS artistId, a2.name AS artistName " +
            "FROM channels c LEFT JOIN channelables ca ON ca.channel_id = c.id " +
            "JOIN albums a on ca.channelable_id = a.id " +
            "JOIN artists a2 on a2.id = a.artist_id " +
            "WHERE ca.channelable_type LIKE '%Album%' AND c.id = :channelId " +
            "GROUP BY a.id " +
            "ORDER By RAND() " +
            "LIMIT :start, :end", nativeQuery = true)
    Optional<List<Map<String, Object>>> findChannelAlbum(Long channelId, int start, int end);
}
