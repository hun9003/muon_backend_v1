package com.muesli.music.interfaces.like;


import com.muesli.music.domain.like.LikeInfo;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface LikeDtoMapper {

    // find
    LikeDto.LikeInfo of(LikeInfo likeInfo);
}
