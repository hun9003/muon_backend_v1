package com.muesli.music.infrastructure.album;

import com.muesli.music.domain.album.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AlbumRepository extends JpaRepository<Album, Long> {
    Optional<Album> findById(Long albumId);

    @Query(value = "SELECT a FROM Album a JOIN Like l ON l.likeableId = a.id WHERE l.likeableType = :likeableType AND l.userId = :userId")
    Optional<List<Album>> findLikeByLikeableTypeAndUserId(String likeableType, Long userId);
}
