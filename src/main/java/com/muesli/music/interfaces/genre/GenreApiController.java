package com.muesli.music.interfaces.genre;

import com.muesli.music.application.genre.GenreFacade;
import com.muesli.music.common.response.CommonResponse;
import com.muesli.music.interfaces.album.AlbumDtoMapper;
import com.muesli.music.interfaces.track.TrackDtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/genre")
public class GenreApiController {

    private final GenreFacade genreFacade;
    private final GenreDtoMapper genreDtoMapper;
    private final AlbumDtoMapper albumDtoMapper;
    private final TrackDtoMapper trackDtoMapper;

//    @GetMapping("/search/{id}")
//    public CommonResponse retrieveGenreItemList(@PathVariable(value = "id") Long genreId, @RequestParam(name = "keyword") String keyword) {
//        System.out.println("GenreApiController :: retrieveGenreItemList");
//        var genreDto = new GenreDto.GenreRequest(keyword, genreId, "");
//        var itemList = genreFacade.getGenreItemList(genreDto.toCommand());
//        return CommonResponse.success(itemList);
//    }

    @GetMapping
    public CommonResponse retrieveGenreAll(@PageableDefault(size = 8, page = 1) Pageable pageable) {
        System.out.println("GenreApiController :: retrieveGenreAll");
        var genreInfoList = genreFacade.retrieveGenreAlbumListAll(pageable);
        var response = genreInfoList.stream().map(genreDtoMapper::of).collect(Collectors.toList());
        return CommonResponse.success(response);
    }

    @GetMapping("/{id}")
    public CommonResponse retrieveGenre(@PathVariable(value = "id") Long genreId, @PageableDefault(size = 8, page = 1) Pageable pageable) {
        System.out.println("GenreApiController :: retrieveGenreAll");
        var albumInfoList = genreFacade.retrieveGenreAlbumList(genreId, pageable);
        var response = albumInfoList.stream().map(albumDtoMapper::of).collect(Collectors.toList());
        return CommonResponse.success(response);
    }

    @GetMapping("/{id}/track")
    public CommonResponse retrieveGenreTrack(@PathVariable(value = "id") Long genreId, @PageableDefault(size = 50, page = 1) Pageable pageable,
                                             @RequestParam(name = "type", required = false, defaultValue = "popularity") String type) {
        System.out.println("GenreApiController :: retrieveGenreAll");
        var trackInfoList = genreFacade.retrieveGenreTrackList(genreId, type, pageable);
        var response = trackInfoList.stream().map(trackDtoMapper::of).collect(Collectors.toList());
        return CommonResponse.success(response);
    }

}
