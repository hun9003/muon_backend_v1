package com.muesli.music.infrastructure.track.lyrics;

import com.muesli.music.domain.track.Track;
import com.muesli.music.domain.track.lyrics.Lyrics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface LyricsRepository extends JpaRepository<Lyrics, Long> {
    Optional<Lyrics> findLyricsByTrack(Track track);

    /**
     * 가사 검색 키워드 조회 (개수)
     * @param keyword
     * @return
     */
    @Query(value = "SELECT COUNT(id) FROM (" +
            "SELECT t.id " +
            "FROM lyrics l JOIN tracks t on l.track_id = t.id JOIN albums a on t.album_id = a.id JOIN artist_track at2 on t.id = at2.track_id JOIN artists a2 on a.artist_id = a2.id " +
            "WHERE REPLACE(l.text, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '') " +
            "OR REPLACE(l.text_original, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '') " +
            "OR REPLACE(l.text_pron, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '') " +
            "OR REPLACE(t.name, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '') " +
            "OR REPLACE(a.name, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '') ) lyrics ", nativeQuery = true)
    Optional<Integer> findSearchLyricsCount(String keyword);

    /**
     * 가사 검색 키워드 조회 (관련도순)
     * @param keyword
     * @param start
     * @param end
     * @return
     */
    @Query(value = "SELECT t.id, t.name, a.id AS albumId, a.name AS albumName, a2.id AS artistId, a2.name AS artistName, l.text AS lyricsText, l.text_pron AS lyricsTextPron, l.text_original AS lyricsTextOriginal, a.image AS albumImage " +
            "FROM lyrics l JOIN tracks t on l.track_id = t.id JOIN albums a on t.album_id = a.id JOIN artist_track at2 on t.id = at2.track_id JOIN artists a2 on a.artist_id = a2.id " +
            "WHERE REPLACE(l.text, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '') " +
            "OR REPLACE(l.text_original, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '') " +
            "OR REPLACE(l.text_pron, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '') " +
            "OR REPLACE(t.name, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '') " +
            "OR REPLACE(a.name, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '') " +
            "GROUP BY l.id " +
            "ORDER BY " +
            "    (CASE WHEN (REPLACE(l.text, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '')) " +
            "                   AND REPLACE(a.name, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '') " +
            "                   AND REPLACE(t.name, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '') THEN 1 " +
            "          WHEN REPLACE(l.text, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '') " +
            "              AND REPLACE(t.name, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '') THEN 2 " +
            "          WHEN REPLACE(l.text, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '') " +
            "              AND REPLACE(a.name, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '') THEN 3 " +
            "          WHEN REPLACE(l.text, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '') THEN 4 " +
            "          WHEN REPLACE(l.text_original, ' ', '') OR REPLACE(l.text_pron, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '') THEN 5 " +
            "          WHEN REPLACE(t.name, ' ', '') OR REPLACE(a.name, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '') THEN 6 " +
            "        ELSE 7 END) " +
            "LIMIT :start, :end", nativeQuery = true)
    Optional<List<Map<String, Object>>> findSearchLyricsOrderBySimilar(String keyword, int start, int end);

    /**
     * 가사 검색 키워드 조회 (가나다순)
     * @param keyword
     * @param start
     * @param end
     * @return
     */
    @Query(value = "SELECT t.id, t.name, a.id AS albumId, a.name AS albumName, a2.id AS artistId, a2.name AS artistName, l.text AS lyricsText, l.text_pron AS lyricsTextPron, l.text_original AS lyricsTextOriginal, a.image AS albumImage " +
            "FROM lyrics l JOIN tracks t on l.track_id = t.id JOIN albums a on t.album_id = a.id JOIN artist_track at2 on t.id = at2.track_id JOIN artists a2 on a.artist_id = a2.id " +
            "WHERE REPLACE(l.text, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '') " +
            "   OR REPLACE(l.text_original, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '') " +
            "   OR REPLACE(l.text_pron, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '') " +
            "   OR REPLACE(t.name, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '') " +
            "   OR REPLACE(a.name, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '') " +
            "GROUP BY l.id " +
            "ORDER BY (CASE WHEN ASCII(SUBSTRING(t.name,1)) BETWEEN 48 AND 57 THEN 3 " +
            "               WHEN ASCII(SUBSTRING(t.name,1)) < 128 THEN 2 ELSE 1 END), t.name " +
            "LIMIT :start, :end", nativeQuery = true)
    Optional<List<Map<String, Object>>> findSearchLyricsOrderByAlpha(String keyword, int start, int end);

    /**
     * 가사 검색 키워드 조회 (최신순)
     * @param keyword
     * @param start
     * @param end
     * @return
     */
    @Query(value = "SELECT t.id, t.name, a.id AS albumId, a.name AS albumName, a2.id AS artistId, a2.name AS artistName, l.text AS lyricsText, l.text_pron AS lyricsTextPron, l.text_original AS lyricsTextOriginal, a.image AS albumImage " +
            "FROM lyrics l JOIN tracks t on l.track_id = t.id JOIN albums a on t.album_id = a.id JOIN artist_track at2 on t.id = at2.track_id JOIN artists a2 on a.artist_id = a2.id " +
            "WHERE REPLACE(l.text, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '') " +
            "OR REPLACE(l.text_original, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '') " +
            "OR REPLACE(l.text_pron, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '') " +
            "OR REPLACE(t.name, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '') " +
            "OR REPLACE(a.name, ' ', '') LIKE REPLACE(CONCAT('%',:keyword,'%'), ' ', '') " +
            "GROUP BY l.id " +
            "ORDER BY a.release_date DESC " +
            "LIMIT :start, :end", nativeQuery = true)
    Optional<List<Map<String, Object>>> findSearchLyricsOrderByNewest(String keyword, int start, int end);
}
