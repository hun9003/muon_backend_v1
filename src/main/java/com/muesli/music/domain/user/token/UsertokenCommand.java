package com.muesli.music.domain.user.token;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.sql.Timestamp;


@Getter
@Builder
@ToString
public class UsertokenCommand {

    private final String token;
    private final Long exp;
    private final Timestamp uploadAt;
    private final Long userId;

    public Usertoken toEntity(Long userId) {
        System.out.println("UsertokenCommand :: toEntity");
        return Usertoken.builder()
                .userId(userId)
                .token(token)
                .exp(exp)
                .uploadAt(uploadAt)
                .build();
    }

}
