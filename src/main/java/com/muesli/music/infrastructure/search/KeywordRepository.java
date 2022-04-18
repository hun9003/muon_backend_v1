package com.muesli.music.infrastructure.search;

import com.muesli.music.domain.search.keyword.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {

    Optional<Keyword> findKeywordBykeyword(String keyword);

    @Query(value = "SELECT * FROM search_keyword k WHERE k.public = 1 AND REPLACE(k.keyword, ' ', '') LIKE REPLACE(CONCAT('%', :keyword, '%'), ' ', '') " +
            "ORDER BY " +
            "(CASE WHEN REPLACE(k.keyword, ' ', '') LIKE REPLACE(CONCAT(:keyword, '%'), ' ', '') THEN 1 " +
            " WHEN REPLACE(k.keyword, ' ', '') LIKE REPLACE(CONCAT('%', :keyword, '%'), ' ', '') THEN 2 " +
            "ELSE 3 END)", nativeQuery = true)
    Optional<List<Keyword>> findKeywordByKeyword(String keyword);
}

