package com.muesli.music.infrastructure.album;

import com.muesli.music.domain.album.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AlbumRepository extends JpaRepository<Album, Long> {

    @Query(value = "SELECT a FROM Album a " +
            "LEFT JOIN FETCH a.likeList " +
            "LEFT JOIN FETCH a.trackList t " +
            "LEFT JOIN FETCH t.lyrics " +
            "LEFT JOIN FETCH t.trackArtist ta " +
            "LEFT JOIN FETCH t.likeList " +
            "LEFT JOIN FETCH ta.artist ar " +
            "LEFT JOIN FETCH ar.bios " +
            "WHERE a.id = :albumId")
    Optional<Album> findAlbumById(Long albumId);

    @Query(value = "SELECT a FROM Album a JOIN FETCH a.likeList l WHERE l.userId = :userId")
    Optional<List<Album>> findLikeAlbum(Long userId);
}
