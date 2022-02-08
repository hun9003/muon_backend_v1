package com.muesli.music.interfaces.like;


import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.ZonedDateTime;

public class LikeDto {

    @Getter
    @Builder
    @ToString
    public static class Main {
        private final Long id;
        private final Long likeable_id;
        private final Long user_id;
        private final String likeable_type;
        private final ZonedDateTime created_at;
    }
}
