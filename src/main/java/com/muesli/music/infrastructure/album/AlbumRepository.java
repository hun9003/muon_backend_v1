package com.muesli.music.infrastructure.album;

import com.muesli.music.domain.album.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AlbumRepository extends JpaRepository<Album, Long> {

    @Query(value = "SELECT a FROM Album a JOIN FETCH a.likeList l WHERE l.likeableType = :likeableType AND l.userId = :userId")
    Optional<List<Album>> findAllJoinFetch(String likeableType, Long userId);
}
