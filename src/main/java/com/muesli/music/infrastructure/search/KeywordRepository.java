package com.muesli.music.infrastructure.search;

import com.muesli.music.domain.search.keyword.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {

    Optional<Keyword> findKeywordBykeyword(String keyword);

    @Query(value = "SELECT * FROM search_keyword k WHERE k.public = 1 " +
            "AND REPLACE(k.keyword, ' ', '') >= REPLACE(:start, ' ', '') " +
            "AND REPLACE(k.keyword, ' ', '') <= REPLACE(:end, ' ', '') " +
            "ORDER BY k.views DESC LIMIT 10", nativeQuery = true)
    Optional<List<Keyword>> findKeywordByKeyword(String start, String end);
}

