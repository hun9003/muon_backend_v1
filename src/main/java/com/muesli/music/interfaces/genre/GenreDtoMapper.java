package com.muesli.music.interfaces.genre;

import com.muesli.music.domain.genre.GenreInfo;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface GenreDtoMapper {

    // find
    GenreDto.GenreInfo of(GenreInfo.Main genreInfo);
}
