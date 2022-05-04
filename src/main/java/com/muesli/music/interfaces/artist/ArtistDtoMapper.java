package com.muesli.music.interfaces.artist;

import com.muesli.music.domain.artist.ArtistInfo;
import com.muesli.music.interfaces.like.LikeDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface ArtistDtoMapper {

    // find
    ArtistDto.Main of(ArtistInfo.Main main);

    ArtistDto.Main2 of(ArtistInfo.Main2 main);

    ArtistDto.ArtistInfo ofItem(ArtistInfo.Main main);

    LikeDto.LikeInfo of(LikeDto.LikeInfo like);

    ArtistDto.ArtistSearchInfo of(ArtistInfo.SearchInfo artistInfo);
}
