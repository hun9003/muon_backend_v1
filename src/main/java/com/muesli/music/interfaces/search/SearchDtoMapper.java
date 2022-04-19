package com.muesli.music.interfaces.search;

import com.muesli.music.domain.search.keyword.KeywordInfo;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;


@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface SearchDtoMapper {
    // find
    SearchDto.SearchKeyword of(KeywordInfo keywordInfo);

}
