package com.muesli.music.infrastructure.album;

import com.muesli.music.domain.album.Album;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlbumRepository extends JpaRepository<Album, Long> {
    Optional<Album> findById(Long albumId);
}
