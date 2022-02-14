package com.muesli.music.interfaces.album;

import com.muesli.music.domain.album.AlbumInfo;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface AlbumDtoMapper {

    // find
    AlbumDto.Main of(AlbumInfo.Main main);

    AlbumDto.LikeInfo of(AlbumInfo.LikeInfo like);

    AlbumDto.TrackInfo of(AlbumInfo.TrackInfo track);

}
