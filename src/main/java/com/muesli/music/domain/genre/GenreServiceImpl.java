package com.muesli.music.domain.genre;

import com.muesli.music.common.util.KeywordScanner;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreReader genreReader;

    /**
     * 장르 리스트
     * @return 장르 대분류 리스트
     */
    @Override
    public List<GenreInfo.Main> getGenreParentList() {
        System.out.println("GenreServiceImpl :: getGenreList");
        var genreList = genreReader.getGenreParentList();
        var initGenre = new Genre(null, "all", "장르종합");
        genreList.add(0, initGenre);
        return genreList.stream().map(GenreInfo.Main::new).collect(Collectors.toList());
    }


    /**
     * 대분류 / 소분류 알파벳, 자음별 리스트 호출
     * @param command 검색 정보가 담긴 데이터 객체
     * @return 장르 아이템 리스트 호출
     */
    @Override
    public List<Object> getGenreItemList(GenreCommand.GenreRequest command) {
        System.out.println("GenreServiceImpl :: getGenreItemList");
        var count = genreReader.getGenreCountByParent(command.getGenreId());
        List<Object> itemList = new ArrayList<>();
        if (count > 0) {
            var endKeyword = KeywordScanner.makeSearchKeyword(command.getKeyword());
            var genreList = genreReader.getGenreItemList(command.getGenreId(), command.getKeyword(), endKeyword);
            var genreInfoList = genreList.stream().map(GenreInfo.Main::new).collect(Collectors.toList());
            itemList.addAll(genreInfoList);
        }
        for(var item : itemList) {
            System.out.println(item);
        }
        return itemList;
    }
}
