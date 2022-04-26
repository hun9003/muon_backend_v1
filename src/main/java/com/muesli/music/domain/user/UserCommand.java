package com.muesli.music.domain.user;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;


public class UserCommand {

    @Getter
    @Builder
    @ToString
    public static class RegisterUserRequest {
        private final String username;
        private final String email;
        private final String password;
        private final String phoneNumber;
        private final Long gender;
        private final String birthDay;

        public User toEntity() {
            System.out.println("UserCommand.RegisterUserRequest :: toEntity");
            return User.builder()
                    .username(username)
                    .email(email)
                    .password(password)
                    .phoneNumber(phoneNumber)
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
