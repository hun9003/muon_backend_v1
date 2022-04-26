package com.muesli.music.domain.user;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;


public class UserCommand {

    @Getter
    @Builder
    @ToString
    public static class RegisterUserRequest {
        private final String username;
        private final String email;
        private final String password;
        private final String phoneNumber;
        private final String gender;
        private final LocalDate birthday;

        public User toEntity() {
            System.out.println("UserCommand.RegisterUserRequest :: toEntity");
            return User.builder()
                    .username(username)
                    .email(email)
                    .password(password)
                    .phoneNumber(phoneNumber)
                    .gender(gender)
                    .birthday(birthday)
                    .build();
        }
    }

    @Getter
    @Builder
    @ToString
    public static class LoginUserRequest {
        private final String email;
        private final String password;

        public User toEntity() {
            System.out.println("UserCommand.LoginUserRequest :: toEntity");
            return User.builder()
                    .email(email)
                    .password(password)
                    .build();
        }
    }
}
