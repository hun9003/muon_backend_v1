package com.muesli.music.interfaces.album;

import com.muesli.music.domain.album.AlbumInfo;
import com.muesli.music.domain.artist.ArtistInfo;
import com.muesli.music.domain.like.LikeInfo;
import com.muesli.music.interfaces.artist.ArtistDto;
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

    AlbumDto.AlbumInfo ofItem(AlbumInfo.Main main);

    LikeDto.LikeInfo of(LikeInfo.Main like);

    ArtistDto.TrackArtistInfo of(ArtistInfo.Main artist);

    AlbumDto.NewestAlbumInfo of(AlbumInfo.NewestAlbumInfo albumInfo);

    AlbumDto.AlbumSearchInfo of(AlbumInfo.SearchInfo albumInfo);

    AlbumDto.GenreAlbumInfo of(AlbumInfo.GenreAlbumInfo albumInfo);

    AlbumDto.ChannelAlbumInfo of(AlbumInfo.ChannelAlbumInfo albumInfo);
}
