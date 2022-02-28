package com.muesli.music.infrastructure.artist;

import com.muesli.music.domain.artist.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ArtistRepository  extends JpaRepository<Artist, Long> {

    @Query(value = "SELECT a FROM Artist a JOIN FETCH a.likeList l LEFT JOIN FETCH a.bios b WHERE l.likeableType = :likeableType AND l.userId = :userId")
    Optional<List<Artist>> findAllJoinFetch(String likeableType, Long userId);

}
