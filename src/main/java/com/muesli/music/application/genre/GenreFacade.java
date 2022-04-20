package com.muesli.music.application.genre;

import com.muesli.music.domain.genre.GenreCommand;
import com.muesli.music.domain.genre.GenreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GenreFacade {
    private final GenreService genreService;

    public List<Object> getGenreItemList(GenreCommand.GenreRequest command) {
        System.out.println("GenreFacade :: getGenreItemList");
        return genreService.getGenreItemList(command);
    }

}
