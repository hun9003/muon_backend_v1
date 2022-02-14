package com.muesli.music.domain.like;

import lombok.Getter;
import lombok.ToString;

import java.time.ZonedDateTime;

@Getter
@ToString
public class LikeInfo {
    private final Long id;
    private final ZonedDateTime createdAt;

    public LikeInfo(Like like) {
        this.id = like.getId();
        this.createdAt = like.getCreated_at();
    }
}
