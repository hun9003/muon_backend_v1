package com.muesli.music.interfaces.track;

import com.muesli.music.domain.like.LikeInfo;
import com.muesli.music.domain.track.TrackInfo;
import com.muesli.music.interfaces.like.LikeDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface TrackDtoMapper {

    // find
    TrackDto.Main of(TrackInfo.Main main);

    TrackDto.TrackInfo ofItem(TrackInfo.Main main);

    LikeDto.LikeInfo of(LikeInfo.Main like);

    TrackDto.LyricsInfo of(TrackInfo.LyricsInfo lyrics);

    TrackDto.TrackRankInfo of(TrackInfo.ChartInfo trackInfo);
}
