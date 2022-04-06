package com.muesli.music.infrastructure.genre;

import com.google.common.collect.Lists;
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
    public List<Genre> getGenreList() {
        System.out.println("GenreReaderImpl :: getGenreList()");
        return genreRepository.findAllBy().orElse(Lists.newArrayList());
    }
}
