package com.muesli.music.domain.user.token;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;


public class UsertokenCommand {

    @Getter
    @Builder
    @ToString
    public static class RegisterUserCommandRequest {

        private final String token;
        private final String exp;
        private final String updateAt;
        private final Long userId;

        public Usertoken toEntity(Long userId) {
            return Usertoken.builder()
                    .userId(userId)
                    .build();
        }
    }
}
