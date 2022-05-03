package com.muesli.music.interfaces.channel;

import com.muesli.music.domain.channel.ChannelInfo;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface ChannelDtoMapper {
    ChannelDto.ChannelInfo of(ChannelInfo.Main channelInfo);
}
