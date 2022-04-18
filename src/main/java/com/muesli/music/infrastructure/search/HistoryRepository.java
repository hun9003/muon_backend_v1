package com.muesli.music.infrastructure.search;

import com.muesli.music.domain.search.history.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface HistoryRepository extends JpaRepository<History, Long> {

    @Query(value = "SELECT h FROM History h WHERE h.userId IS NOT NULL AND h.resultCount = 1 GROUP BY h.keyword HAVING COUNT(h.id) >= 15")
    Optional<List<History>> findHistoryAll();

    @Query(value = "SELECT h FROM History h WHERE h.keyword = :keyword AND (h.userId = :userId OR h.ip = :ip)")
    Optional<History> findHistoryByKeyword(String keyword, Long userId, String ip);
}
