package com.muesli.music.interfaces.user;

import com.muesli.music.domain.user.UserCommand;
import com.muesli.music.domain.user.UserInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class UserDto {

    @Getter
    @Setter
    @ToString
    public static class RegisterUser {

        @NotEmpty(message = "닉네임(username)은 필수값입니다.")
        private String username;

        @Email(message = "email 형식에 맞아야 합니다")
        @NotEmpty(message = "이메일(email)은 필수값입니다.")
        private String email;

        @Length(min = 8, max = 30)
        @NotEmpty(message = "비밀번호(password)는 필수값입니다.")
        private String password;

        @NotEmpty(message = "전화번호(phoneNumber)은 필수값입니다.")
        private String phoneNumber;

        public UserCommand toCommand() {
            return UserCommand.builder()
                    .username(username)
                    .email(email)
                    .password(password)
                    .phoneNumber(phoneNumber)
                    .build();
        }
    }

    @Getter
    @Setter
    @ToString
    public static class LoginUser {
        @Email(message = "email 형식에 맞아야 합니다")
        @NotEmpty(message = "이메일(email)은 필수값입니다.")
        private String email;

        @Length(min = 8, max = 30)
        @NotEmpty(message = "비밀번호(password)는 필수값입니다.")
        private String password;

        public UserCommand toCommand() {
            return UserCommand.builder()
                    .email(email)
                    .password(password)
                    .build();
        }
    }

    @Getter
    @ToString
    public static class RegisterResponse {
        private final String username;
        private final String email;
        private final String phoneNumber;

        public RegisterResponse(UserInfo.Main userInfo) {
            this.username = userInfo.getUsername();
            this.email = userInfo.getEmail();
            this.phoneNumber = userInfo.getPhoneNumber();
        }
    }

    @Getter
    @Builder
    @ToString
    public static class LoginResponse {
        private final String token;
        private final Long exp;
        private final UserInfo user;
    }



}
