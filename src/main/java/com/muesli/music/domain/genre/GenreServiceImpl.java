package com.muesli.music.domain.genre;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreReader genreReader;

    /**
     * 장르 리스트
     * @return
     */
    @Override
    public List<GenreInfo.Main> getGenreList() {
        System.out.println("GenreServiceImpl :: getGenreList");
        var genreList = genreReader.getGenreList();
        return genreList.stream().map(GenreInfo.Main::new).collect(Collectors.toList());
    }
}
