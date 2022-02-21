package com.muesli.music.interfaces.album;

import com.muesli.music.domain.album.AlbumInfo;
import com.muesli.music.domain.like.LikeInfo;
import com.muesli.music.interfaces.like.LikeDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import com.muesli.music.domain.track.TrackInfo;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface AlbumDtoMapper {

    // find
    AlbumDto.Main of(AlbumInfo.Main main);

    AlbumDto.AlbumInfo ofItem(AlbumInfo.Main main);

    LikeDto.LikeInfo of(LikeInfo.Main like);

    AlbumDto.TrackInfo of(TrackInfo.Main track);

}
