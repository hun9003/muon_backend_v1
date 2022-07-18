package com.muesli.music.interfaces.user;

import com.muesli.music.common.util.Message;
import com.muesli.music.domain.user.UserCommand;
import com.muesli.music.domain.user.UserInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

public class UserDto {

    @Getter
    @Setter
    @ToString
    @Builder
    public static class RegisterUser {

        @NotBlank(message = Message.User.EMPTY_USERNAME)
        @NotEmpty(message = Message.User.EMPTY_USERNAME)
        private String username;

        @NotBlank(message = Message.User.EMPTY_EMAIL)
        @NotEmpty(message = Message.User.EMPTY_EMAIL)
        @Email(message = Message.User.BAD_EMAIL_PATTERN)
        private String email;

        @NotBlank(message = Message.User.EMPTY_PASSWORD)
        @NotEmpty(message = Message.User.EMPTY_PASSWORD)
        @Length(min = 8, max = 30, message = Message.User.BAD_PASSWORD_PATTERN)
        private String password;

        @Pattern(regexp = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$", message = Message.User.BAD_PHONENUMBER_PATTERN)
        private String phoneNumber;

        private String gender;

        private LocalDate birthday;

        private String authType;

        public UserCommand.RegisterUserRequest toCommand() {
            String authType = this.authType != null ? this.authType : "local";
            return UserCommand.RegisterUserRequest.builder()
                    .username(username)
                    .email(email)
                    .password(password)
                    .phoneNumber(phoneNumber)
                    .gender(gender)
                    .birthday(birthday)
                    .authType(authType)
                    .build();
        }
    }

    @Getter
    @Setter
    @ToString
    public static class ChangeConfirmedRequest {
        private String email;
    }

    @Getter
    @Setter
    @ToString
    @Builder
    public static class LoginUser {
        @Email(message = "email 형식에 맞아야 합니다")
        @NotEmpty(message = "이메일(email)은 필수값입니다.")
        private String email;

        @Length(min = 8, max = 30)
        @NotEmpty(message = "비밀번호(password)는 필수값입니다.")
        private String password;

        public UserCommand.LoginUserRequest toCommand() {
            return UserCommand.LoginUserRequest.builder()
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
        private final String gender;
        private final LocalDate birthday;

        public RegisterResponse(UserInfo.Main userInfo) {
            this.username = userInfo.getUsername();
            this.email = userInfo.getEmail();
            this.phoneNumber = userInfo.getPhoneNumber();
            this.gender = userInfo.getGender();
            this.birthday = userInfo.getBirthday();
        }
    }

    @Getter
    @ToString
    public static class LoginResponse {
        private final String token;
        private final Long exp;
        private final UserInfo.Main user;

        public LoginResponse(UserInfo.UsertokenInfo usertokenInfo) {
            this.token = usertokenInfo.getToken();
            this.exp = usertokenInfo.getExp();
            this.user = usertokenInfo.getUserInfo();
        }
    }

    @Getter
    @Builder
    @ToString
    public static class PlaylistUserInfo {
        private final Long id;
        private final String username;
    }

    @Getter
    @Builder
    @ToString
    public static class ChangePasswordRequest {
        private final String password;
        private final String newPassword;

        public UserCommand.ChangeUserPassword toCommand() {
            return UserCommand.ChangeUserPassword.builder()
                    .password(password)
                    .newPassword(newPassword)
                    .build();
        }
    }

}
