package com.muesli.music.domain.like;

import lombok.Getter;
import lombok.ToString;

import java.time.ZonedDateTime;


public class LikeInfo {

    @Getter
    @ToString
    public static class Main {
        private final Long id;
        private final Long userId;
        private final String likeableType;
        private final ZonedDateTime createdAt;
        private final int isLike;

        private Long likeCount;

        public Main(Like like) {
            this.id = like.getId();
            this.userId = like.getUserId();
            this.likeableType = like.getLikeableType();
            this.isLike = like.getIsLike();
            this.createdAt = like.getCreated_at();
        }

        public Main(Like like, Long likeCount) {
            this.id = like.getId();
            this.userId = like.getUserId();
            this.likeableType = like.getLikeableType();
            this.isLike = like.getIsLike();
            this.createdAt = like.getCreated_at();
            this.likeCount = likeCount;
        }

        public void setLikeCount(Long likeCount) {
            this.likeCount = likeCount;
        }
    }
    }
