package com.muesli.music.domain.user;

import com.muesli.music.common.util.TokenGenerator;
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
        private final String authType;

        public User toEntity() {
            System.out.println("UserCommand.RegisterUserRequest :: toEntity");
            return User.builder()
                    .username(username)
                    .email(email)
                    .password(password)
                    .phoneNumber(phoneNumber)
                    .gender(gender)
                    .birthday(birthday)
                    .authType(authType)
                    .build();
        }

        public User toSocialEntity(String email, String username, String authType) {
            return User.builder()
                    .email(email)
                    .username(username)
                    .password(TokenGenerator.randomCharacter(32))
                    .authType(authType)
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

    @Getter
    @Builder
    @ToString
    public static class ChangeUserPassword {
        private final String password;
        private final String newPassword;

    }

    @Getter
    @Builder
    @ToString
    public static class SocialLoginRequest {
        private final String accessToken;
        private final String authType;

        public SocialLoginRequest(String accessToken, String authType) {
            this.accessToken = accessToken;
            this.authType = authType;
        }
    }
}
