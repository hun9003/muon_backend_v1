package com.muesli.music.infrastructure.genre;

import com.muesli.music.domain.genre.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre, Long> {

    Optional<List<Genre>> findAllBy();
}
