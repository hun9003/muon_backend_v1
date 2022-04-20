package com.muesli.music.interfaces.genre;

import com.muesli.music.application.genre.GenreFacade;
import com.muesli.music.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/genre")
public class GenreApiController {

    private final GenreFacade genreFacade;

    @GetMapping("/search/{id}")
    public CommonResponse retrieveGenreItemList(@PathVariable(value = "id") Long genreId, @RequestParam(name = "keyword") String keyword) {
        System.out.println("GenreApiController :: retrieveGenreItemList");
        var genreDto = new GenreDto.GenreRequest(keyword, genreId, "");
        var itemList = genreFacade.getGenreItemList(genreDto.toCommand());
        return CommonResponse.success(itemList);
    }
}
