package com.muesli.music.domain.user;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;


@Getter
@Builder
@ToString
public class UserCommand {
    private final String username;
    private final String email;
    private final String password;
    private final String phone_number;

    public User toEntity() {
        return User.builder()
                .username(username)
                .email(email)
                .password(password)
                .phone_number(phone_number)
                .build();
    }
}
