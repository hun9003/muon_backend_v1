package com.muesli.music.interfaces.like;


import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.ZonedDateTime;

public class LikeDto {

    @Getter
    @Builder
    @ToString
    public static class LikeInfo {
        private final Long id;
        private final ZonedDateTime createdAt;
    }
}
