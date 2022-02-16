package com.muesli.music.domain.like;

import lombok.Getter;
import lombok.ToString;

import java.time.ZonedDateTime;

@Getter
@ToString
public class LikeInfo {
    private final Long id;
    private final Long likeableId;
    private final Long userId;
    private final String likeableType;
    private final ZonedDateTime createdAt;
    private final int isLike;

    private Long likeCount;

    public LikeInfo(Like like) {
        this.id = like.getId();
        this.likeableId = like.getLikeableId();
        this.userId = like.getUserId();
        this.likeableType = like.getLikeableType();
        this.isLike = like.getIsLike();
        this.createdAt = like.getCreated_at();
    }

    public LikeInfo(Like like, Long likeCount) {
        this.id = like.getId();
        this.likeableId = like.getLikeableId();
        this.userId = like.getUserId();
        this.likeableType = like.getLikeableType();
        this.isLike = like.getIsLike();
        this.createdAt = like.getCreated_at();
        this.likeCount = likeCount;
    }
}
