package com.muesli.music.infrastructure.track;

import com.muesli.music.domain.track.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;
import java.util.Optional;


public interface TrackRepository  extends JpaRepository<Track, Long> {

    /**
     * 트랙 PK로 조회
     * @param trackId
     * @return
     */
    @Query(value = "SELECT t FROM Track t " +
            "LEFT JOIN FETCH t.likeList l " +
            "LEFT JOIN FETCH t.lyrics " +
            "LEFT JOIN FETCH t.trackArtists ta " +
            "LEFT JOIN FETCH ta.artist a " +
            "LEFT JOIN FETCH a.bios " +
            "WHERE t.id = :trackId")
    Optional<Track> findTrackById(Long trackId);

    /**
     * 좋아요 한 트랙 리스트 개수
     * @param userId
     * @return
     */
    @Query(value = "SELECT COUNT(id) FROM (" +
            "SELECT t.id FROM tracks t " +
            "JOIN artist_track at on t.id = at.track_id " +
            "JOIN artists a2 on at.artist_id = a2.id " +
            "JOIN albums a on t.album_id = a.id " +
            "JOIN likes l on l.likeable_id = t.id " +
            "WHERE l.user_id = :userId AND l.likeable_type LIKE '%Track%' AND l.is_like = 1 " +
            "GROUP BY t.id) AS track", nativeQuery = true)
    Optional<Integer> countLikeList(Long userId);

    /**
     * 좋아요 한 트랙 리스트
     * @param userId
     * @param start
     * @param end
     * @return
     */
    @Query(value = "SELECT t.id, t.name, t.number, t.duration, " +
            "            t.description, t.image, t.adult, t.is_title, " +
            "            a.id AS albumId, a.name AS albumName, a.image AS albumImage, " +
            "            a2.id AS artistId, a2.name AS artistName  FROM tracks t " +
            "JOIN artist_track at on t.id = at.track_id " +
            "JOIN artists a2 on at.artist_id = a2.id " +
            "JOIN albums a on t.album_id = a.id " +
            "JOIN likes l on l.likeable_id = t.id " +
            "WHERE l.user_id = :userId AND l.likeable_type LIKE '%Track%' AND l.is_like = 1 " +
            "GROUP BY t.id " +
            "ORDER BY l.updated_at DESC " +
            "LIMIT :start, :end", nativeQuery = true)
    Optional<List<Map<String, Object>>> findLikeList(Long userId, int start, int end);

    /**
     * 앨범 PK로 트랙 리스트 조회
     * @param albumId
     * @return
     */
    @Query(value = "SELECT t FROM Track t " +
            "LEFT JOIN FETCH t.trackArtists ta " +
            "LEFT JOIN FETCH ta.artist a " +
            "LEFT JOIN FETCH t.lyrics l " +
            "LEFT JOIN FETCH t.likeList " +
            "LEFT JOIN FETCH a.bios " +
            "WHERE t.album.id = :albumId")
    Optional<List<Track>> findTrackByAlbumId(Long albumId);

    /**
     * 앨범에 속한 트랙 개수
     * @param albumId 앨범 idx
     * @return 트랙 개수
     */
    Optional<Integer> countTrackByAlbumId(Long albumId);

    /**
     * 앨범에 속한 트랙리스트
     * @param albumId
     * @param start
     * @param end
     * @return
     */
    @Query(value = "SELECT t.id, t.name, t.number, t.duration, " +
            "            t.description, t.image, t.adult, t.is_title, " +
            "            a.id AS albumId, a.name AS albumName, a.image AS albumImage, " +
            "            a2.id AS artistId, a2.name AS artistName  FROM tracks t " +
            "JOIN artist_track at on t.id = at.track_id " +
            "JOIN artists a2 on at.artist_id = a2.id " +
            "JOIN albums a on t.album_id = a.id " +
            "WHERE t.album_id = :albumId " +
            "GROUP BY t.id " +
            "ORDER BY t.id " +
            "LIMIT :start, :end", nativeQuery = true)
    Optional<List<Map<String, Object>>> findTrackByAlbumId(Long albumId, int start, int end);

    /**
     * 아티스트에 속한 트랙 개수
     * @param artistId 아티스트 idx
     * @return 트랙 개수
     */
    @Query(value = "SELECT COUNT(id) FROM (" +
            "SELECT t.id FROM tracks t " +
            "JOIN artist_track at on t.id = at.track_id " +
            "JOIN artists a2 on at.artist_id = a2.id " +
            "JOIN albums a on t.album_id = a.id " +
            "WHERE a2.id = :artistId " +
            "GROUP BY t.id ) as track", nativeQuery = true)
    Optional<Integer> countTrackByArtistId(Long artistId);

    /**
     * 아티스트에 속한 트랙리스트
     * @param artistId 아티스트 idx
     * @param start
     * @param end
     * @return
     */
    @Query(value = "SELECT t.id, t.name, t.number, t.duration, " +
            "            t.description, t.image, t.adult, t.is_title, " +
            "            a.id AS albumId, a.name AS albumName, a.image AS albumImage, " +
            "            a2.id AS artistId, a2.name AS artistName  FROM tracks t " +
            "JOIN artist_track at on t.id = at.track_id " +
            "JOIN artists a2 on at.artist_id = a2.id " +
            "JOIN albums a on t.album_id = a.id " +
            "WHERE a2.id = :artistId " +
            "GROUP BY t.id " +
            "ORDER BY t.id " +
            "LIMIT :start, :end", nativeQuery = true)
    Optional<List<Map<String, Object>>> findTrackByArtistId(Long artistId, int start, int end);

    /**
     * 트랙 순위 리스트 조회
     * @param beginDate
     * @param endDate
     * @param limit
     * @return
     */
    @Query(value = "SELECT count(t.id) AS playCount, (" +
            "    SELECT count(*) FROM likes WHERE likeable_id = t.id AND likeable_type LIKE '%Track%' " +
            "        ) as likeCount, t.id, t.name, t.number, t.duration, " +
            "t.description, t.image, t.adult, t.is_title, " +
            "a.id AS albumId, a.name AS albumName, a.image AS albumImage, " +
            "a2.id AS artistId, a2.name AS artistName " +
            "FROM play_log p JOIN tracks t ON p.track_id = t.id " +
            "JOIN albums a on t.album_id = a.id " +
            "JOIN artist_track at2 on t.id = at2.track_id " +
            "JOIN artists a2 on at2.artist_id = a2.id " +
            "WHERE p.created_at > :beginDate AND p.created_at < :endDate " +
            "GROUP BY t.id " +
            "ORDER By count(t.id) DESC, likecount DESC " +
            "LIMIT :limit", nativeQuery = true)
    Optional<List<Map<String, Object>>> findTrackRank(String beginDate, String endDate, int limit);

    /**
     * 트랙 장르별 순위 리스트 조회
     * @param beginDate
     * @param endDate
     * @param limit
     * @param genreId
     * @return
     */
    @Query(value = "SELECT count(t.id) AS playCount, (" +
            "    SELECT count(*) FROM likes WHERE likeable_id = t.id AND likeable_type LIKE '%Track%' " +
            "        ) as likeCount, t.id, t.name, t.number, t.duration, " +
            "t.description, t.image, t.adult, t.is_title, " +
            "a.id AS albumId, a.name AS albumName, a.image AS albumImage, " +
            "a2.id AS artistId, a2.name AS artistName " +
            "FROM play_log p JOIN tracks t ON p.track_id = t.id " +
            "JOIN albums a on t.album_id = a.id " +
            "JOIN artist_track at2 on t.id = at2.track_id " +
            "JOIN artists a2 on at2.artist_id = a2.id " +
            "JOIN genreables ga on ga.genreable_id = t.id " +
            "JOIN genres g on ga.genre_id = g.id " +
            "WHERE p.created_at > :beginDate AND p.created_at < :endDate " +
            "AND ga.genreable_type Like '%Track%' AND g.id = :genreId " +
            "GROUP BY t.id " +
            "ORDER By count(t.id) DESC, likecount DESC " +
            "LIMIT :limit", nativeQuery = true)
    Optional<List<Map<String, Object>>> findTrackGenreRank(String beginDate, String endDate, int limit, Long genreId);

    /**
     * 최신 곡 리스트 조회
     * @param start
     * @param end
     * @return
     */
    @Query(value = "SELECT t.id, t.name, t.number, t.duration, " +
            "t.description, t.image, t.adult, t.is_title, " +
            "a.id AS albumId, a.name AS albumName, a.image AS albumImage, " +
            "a2.id AS artistId, a2.name AS artistName " +
            "FROM tracks t " +
            "JOIN albums a on t.album_id = a.id " +
            "JOIN artist_track at2 on t.id = at2.track_id " +
            "JOIN artists a2 on at2.artist_id = a2.id " +
            "GROUP BY a.id " +
            "ORDER By a.release_date DESC " +
            "LIMIT :start, :end", nativeQuery = true)
    Optional<List<Map<String, Object>>> findNewTrack(int start, int end);

    /**
     * 최근 들은 곡 리스트 조회
     * @param userId
     * @param start
     * @param end
     * @return
     */
    @Query(value = "SELECT t.id, t.name, t.number, t.duration, " +
            "t.description, t.image, t.adult, t.is_title, " +
            "a.id AS albumId, a.name AS albumName, a.image AS albumImage, " +
            "a2.id AS artistId, a2.name AS artistName " +
            "FROM play_log p JOIN tracks t ON p.track_id = t.id " +
            "JOIN albums a on t.album_id = a.id " +
            "JOIN artist_track at2 on t.id = at2.track_id " +
            "JOIN artists a2 on at2.artist_id = a2.id " +
            "WHERE p.user_id = :userId " +
            "GROUP BY t.id " +
            "ORDER By p.created_at DESC " +
            "LIMIT :start, :end", nativeQuery = true)
    Optional<List<Map<String, Object>>> findUserHistoryTrack(Long userId, int start, int end);

    /**
     * 트랙 검색 키워드 조회 (개수)
     * @param keyword
     * @return
     */
    @Query(value = "SELECT COUNT(id) FROM (" +
            "SELECT t.id as id " +
            "FROM tracks t " +
            "JOIN albums a on t.album_id = a.id " +
            "JOIN artist_track at2 on t.id = at2.track_id " +
            "JOIN artists a2 on at2.artist_id = a2.id " +
            "WHERE (REPLACE(t.name, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '') AND REPLACE(a.name, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '')) " +
            "OR REPLACE(t.name, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '') " +
            "OR REPLACE(a2.name, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '') " +
            "OR REPLACE(a.name, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '') " +
            "OR REPLACE(t.description, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '') " +
            "GROUP BY t.id) as track", nativeQuery = true)
    Optional<Integer> findSearchTrackCount(String keyword);

    /**
     * 트랙 검색 키워드 조회 (인기순)
     * @param keyword
     * @param start
     * @param end
     * @return
     */
    @Query(value = "SELECT t.id, t.name, t.number, t.duration, " +
            "t.description, t.image, t.adult, t.is_title, " +
            "a.id AS albumId, a.name AS albumName, a.image AS albumImage, a.release_date AS albumReleaseDate, " +
            "a2.id AS artistId, a2.name AS artistName " +
            "FROM tracks t JOIN play_log p ON p.track_id = t.id " +
            "JOIN albums a on t.album_id = a.id " +
            "JOIN artist_track at2 on t.id = at2.track_id " +
            "JOIN artists a2 on at2.artist_id = a2.id " +
            "WHERE (REPLACE(t.name, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '') AND REPLACE(a.name, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '')) " +
            "OR REPLACE(t.name, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '') " +
            "OR REPLACE(a2.name, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '') " +
            "OR REPLACE(a.name, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '') " +
            "OR REPLACE(t.description, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '') " +
            "GROUP BY t.id " +
            "ORDER By COUNT(p.id) DESC " +
            "LIMIT :start, :end", nativeQuery = true)
    Optional<List<Map<String, Object>>> findSearchTrackOrderByPopularity(String keyword, int start, int end);

    /**
     * 트랙 검색 키워드 조회 (관련도순)
     * @param keyword
     * @param start
     * @param end
     * @return
     */
    @Query(value = "SELECT t.id, t.name, t.number, t.duration, " +
            "t.description, t.image, t.adult, t.is_title, " +
            "a.id AS albumId, a.name AS albumName, a.image AS albumImage, a.release_date AS albumReleaseDate, " +
            "a2.id AS artistId, a2.name AS artistName " +
            "FROM tracks t JOIN play_log p ON p.track_id = t.id " +
            "JOIN albums a on t.album_id = a.id " +
            "JOIN artist_track at2 on t.id = at2.track_id " +
            "JOIN artists a2 on at2.artist_id = a2.id " +
            "WHERE (REPLACE(t.name, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '') AND REPLACE(a.name, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '')) " +
            "OR REPLACE(t.name, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '') " +
            "OR REPLACE(a2.name, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '') " +
            "OR REPLACE(a.name, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '') " +
            "OR REPLACE(t.description, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '') " +
            "GROUP BY t.id " +
            "ORDER By " +
            "(CASE WHEN (REPLACE(t.name, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '') AND REPLACE(a.name, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '')) THEN 1 " +
            "    WHEN REPLACE(t.name, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '') THEN 2 " +
            "    WHEN REPLACE(a2.name, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '') THEN 3 " +
            "    WHEN REPLACE(a.name, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '') THEN 4 " +
            "     ELSE 5 END) " +
            "LIMIT :start, :end", nativeQuery = true)
    Optional<List<Map<String, Object>>> findSearchTrackOrderBySimilar(String keyword, int start, int end);

    /**
     * 트랙 검색 키워드로 조회 (최신순)
     * @param keyword
     * @param start
     * @param end
     * @return
     */
    @Query(value = "SELECT t.id, t.name, t.number, t.duration, " +
            "t.description, t.image, t.adult, t.is_title, " +
            "a.id AS albumId, a.name AS albumName, a.image AS albumImage, a.release_date AS albumReleaseDate, " +
            "a2.id AS artistId, a2.name AS artistName " +
            "FROM tracks t JOIN play_log p ON p.track_id = t.id " +
            "JOIN albums a on t.album_id = a.id " +
            "JOIN artist_track at2 on t.id = at2.track_id " +
            "JOIN artists a2 on at2.artist_id = a2.id " +
            "WHERE (REPLACE(t.name, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '') AND REPLACE(a.name, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '')) " +
            "OR REPLACE(t.name, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '') " +
            "OR REPLACE(a2.name, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '') " +
            "OR REPLACE(a.name, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '') " +
            "OR REPLACE(t.description, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '') " +
            "GROUP BY t.id " +
            "ORDER By a.release_date DESC " +
            "LIMIT :start, :end", nativeQuery = true)
    Optional<List<Map<String, Object>>> findSearchTrackOrderByNewest(String keyword, int start, int end);


    /**
     * 트랙 장르별 개수 조회
     * @param genreId
     * @return
     */
    @Query(value = "SELECT COUNT(id) FROM (" +
            "SELECT t.id as id " +
            "FROM genres g JOIN genreables g2 ON g.id = g2.genre_id " +
            "JOIN tracks t ON t.id = g2.genreable_id " +
            "JOIN play_log p ON p.track_id = t.id " +
            "JOIN albums a on t.album_id = a.id " +
            "JOIN artist_track at2 on t.id = at2.track_id " +
            "JOIN artists a2 on at2.artist_id = a2.id " +
            "WHERE g.id = :genreId AND g2.genreable_type LIKE CONCAT('%','Track') " +
            "GROUP BY t.id) as track", nativeQuery = true)
    Optional<Integer> findGenreTrackCount(Long genreId);


    /**
     * 트랙 장르별 조회 (인기순)
     * @param start
     * @param end
     * @return
     */
    @Query(value = "SELECT t.id, t.name, t.number, t.duration, " +
            "t.description, t.image, t.adult, t.is_title, " +
            "a.id AS albumId, a.name AS albumName, a.image AS albumImage, a.release_date AS albumReleaseDate, " +
            "a2.id AS artistId, a2.name AS artistName " +
            "FROM genres g JOIN genreables g2 ON g.id = g2.genre_id " +
            "JOIN tracks t ON t.id = g2.genreable_id " +
            "JOIN play_log p ON p.track_id = t.id " +
            "JOIN albums a on t.album_id = a.id " +
            "JOIN artist_track at2 on t.id = at2.track_id " +
            "JOIN artists a2 on at2.artist_id = a2.id " +
            "WHERE g.id = :genreId AND g2.genreable_type LIKE CONCAT('%','Track') " +
            "GROUP BY t.id " +
            "ORDER By COUNT(p.id) DESC " +
            "LIMIT :start, :end", nativeQuery = true)
    Optional<List<Map<String, Object>>> findGenreTrackOrderByPopularity(Long genreId, int start, int end);

    /**
     * 트랙 장르별 조회 (최신순)
     * @param start
     * @param end
     * @return
     */
    @Query(value = "SELECT t.id, t.name, t.number, t.duration, " +
            "t.description, t.image, t.adult, t.is_title, " +
            "a.id AS albumId, a.name AS albumName, a.image AS albumImage, a.release_date AS albumReleaseDate, " +
            "a2.id AS artistId, a2.name AS artistName " +
            "FROM genres g JOIN genreables g2 ON g.id = g2.genre_id " +
            "JOIN tracks t ON t.id = g2.genreable_id " +
            "JOIN play_log p ON p.track_id = t.id " +
            "JOIN albums a on t.album_id = a.id " +
            "JOIN artist_track at2 on t.id = at2.track_id " +
            "JOIN artists a2 on at2.artist_id = a2.id " +
            "WHERE g.id = :genreId AND g2.genreable_type LIKE CONCAT('%','Track') " +
            "GROUP BY t.id " +
            "ORDER By a.release_date DESC " +
            "LIMIT :start, :end", nativeQuery = true)
    Optional<List<Map<String, Object>>> findGenreTrackOrderByNewest(Long genreId, int start, int end);

    @Query(value = "SELECT COUNT(id) FROM (" +
            "SELECT t.id FROM tracks t " +
            "JOIN artist_track at on t.id = at.track_id " +
            "JOIN artists a2 on at.artist_id = a2.id " +
            "JOIN albums a on t.album_id = a.id " +
            "JOIN playlist_track pt on pt.track_id = t.id " +
            "JOIN playlists p on p.id = pt.playlist_id " +
            "WHERE p.id = :playlistId " +
            "GROUP BY t.id) as track", nativeQuery = true)
    Optional<Integer> countTrackByPlaylist(Long playlistId);

    @Query(value = "SELECT t.id, t.name, t.number, t.duration, " +
            "            t.description, t.image, t.adult, t.is_title, " +
            "            a.id AS albumId, a.name AS albumName, a.image AS albumImage, " +
            "            a2.id AS artistId, a2.name AS artistName  FROM tracks t " +
            "JOIN artist_track at on t.id = at.track_id " +
            "JOIN artists a2 on at.artist_id = a2.id " +
            "JOIN albums a on t.album_id = a.id " +
            "JOIN playlist_track pt on pt.track_id = t.id " +
            "JOIN playlists p on p.id = pt.playlist_id " +
            "WHERE p.id = :playlistId " +
            "GROUP BY t.id " +
            "ORDER BY pt.id DESC " +
            "LIMIT :start, :end", nativeQuery = true)
    Optional<List<Map<String, Object>>> findTrackByPlaylist(Long playlistId, int start, int end);
}
