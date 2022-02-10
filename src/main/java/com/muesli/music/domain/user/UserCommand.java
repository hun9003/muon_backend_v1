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
    private final String phoneNumber;

    public User toEntity() {
        System.out.println("UserCommand :: toEntity");
        return User.builder()
                .username(username)
                .email(email)
                .password(password)
                .phoneNumber(phoneNumber)
                .build();
    }
}
