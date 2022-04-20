package com.muesli.music.infrastructure.genre;

import com.google.common.collect.Lists;
import com.muesli.music.common.exception.EntityNotFoundException;
import com.muesli.music.domain.genre.Genre;
import com.muesli.music.domain.genre.GenreReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class GenreReaderImpl implements GenreReader {
    private final GenreRepository genreRepository;

    @Override
    public List<Genre> getGenreParentList() {
        System.out.println("GenreReaderImpl :: getGenreList()");
        return genreRepository.findAllByParentId(0L).orElse(Lists.newArrayList());
    }

    @Override
    public Genre getGenreById(Long genreId) {
        System.out.println("GenreReaderImpl :: getGenreById()");
        return genreRepository.findById(genreId).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public int getGenreCountByParent(Long genreId) {
        System.out.println("GenreReaderImpl :: getGenreCountByParent()");
        return genreRepository.countByParentId(genreId).orElse(0);
    }

    @Override
    public List<Genre> getGenreItemList(Long genreId, String start, String end) {
        System.out.println("GenreReaderImpl :: getGenreItemList()");
        return genreRepository.findGenreByKeyword(genreId, start, end).orElse(Lists.newArrayList());
    }
}
