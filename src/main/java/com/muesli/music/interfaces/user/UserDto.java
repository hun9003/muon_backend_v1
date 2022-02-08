package com.muesli.music.interfaces.user;

import com.muesli.music.domain.user.UserCommand;
import com.muesli.music.domain.user.UserInfo;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class UserDto {

    public static class RegisterUser {

        @NotEmpty(message = "닉네임(username)은 필수값입니다.")
        private String username;

        @Email(message = "email 형식에 맞아야 합니다")
        @NotEmpty(message = "이메일(email)은 필수값입니다.")
        private String email;

        @NotEmpty(message = "비밀번호(password)는 필수값입니다.")
        private String password;

        @NotEmpty(message = "전화번호(phone_number)은 필수값입니다.")
        private String phone_number;

        public UserCommand toCommand() {
            return UserCommand.builder()
                    .username(username)
                    .email(email)
                    .password(password)
                    .phone_number(phone_number)
                    .build();
        }
    }

    @Getter
    @ToString
    public static class RegisterResponse {
        private final String username;
        private final String email;
        private final String phone_number;

        public RegisterResponse(UserInfo userInfo) {
            this.username = userInfo.getUsername();
            this.email = userInfo.getEmail();
            this.phone_number = userInfo.getPhone_number();
        }
    }
}
