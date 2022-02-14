package com.muesli.music.interfaces.album;

import com.muesli.music.domain.album.AlbumInfo;
import com.muesli.music.domain.like.Like;
import com.muesli.music.domain.like.LikeInfo;
import com.muesli.music.interfaces.like.LikeDto;
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

    LikeDto.LikeInfo of(LikeInfo like);

    AlbumDto.TrackInfo of(AlbumInfo.TrackInfo track);

}
