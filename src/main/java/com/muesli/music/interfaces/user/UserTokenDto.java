package com.muesli.music.interfaces.user;

import com.muesli.music.domain.user.User;
import com.muesli.music.domain.user.token.UsertokenCommand;
import lombok.Getter;
import lombok.ToString;

import java.sql.Timestamp;

public class UserTokenDto {

    @Getter
    @ToString
    public static class RegisterUserToken {

        String token;
        Long exp;
        Timestamp uploadAt;
        User user;


        public UsertokenCommand toCommand() {
            return UsertokenCommand.builder()
                    .token(token)
                    .exp(exp)
                    .uploadAt(uploadAt)
                    .user(user)
                    .build();
        }
    }
}
