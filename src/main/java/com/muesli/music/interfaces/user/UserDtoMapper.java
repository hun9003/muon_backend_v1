package com.muesli.music.interfaces.user;

import com.muesli.music.domain.user.UserCommand;
import com.muesli.music.domain.user.token.UsertokenCommand;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface UserDtoMapper {
    UserCommand of(UserDto.RegisterUser request);
    UsertokenCommand of(UserTokenDto.RegisterUserToken request);
}
