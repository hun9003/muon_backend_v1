package com.muesli.music.interfaces.playlist;

import com.muesli.music.domain.playlist.PlaylistCommand;
import com.muesli.music.domain.playlist.PlaylistInfo;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;


@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface PlaylistDtoMapper {
    // register
    PlaylistCommand.RegisterPlaylistRequest of(PlaylistDto.RegisterPlaylist request);

    PlaylistCommand.TrackToPlaylistRequest of(PlaylistDto.PlaylistTracksRequest request);

    // find
    PlaylistDto.PlaylistInfo of(PlaylistInfo.Main playlist);

}
