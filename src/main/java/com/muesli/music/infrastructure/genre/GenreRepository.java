package com.muesli.music.infrastructure.genre;

import com.muesli.music.domain.genre.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre, Long> {

    Optional<List<Genre>> findAllByParentId(Long parentId);

    @Query(value = "SELECT g FROM Genre g WHERE g.displayName >= :start AND g.displayName <= :end AND g.parentId = :genreId")
    Optional<List<Genre>> findGenreByKeyword(Long genreId, String start, String end);

    @Query(value = "SELECT COUNT(g.id) FROM Genre g WHERE g.parentId = :genreId")
    Optional<Integer> countByParentId(Long genreId);

}
