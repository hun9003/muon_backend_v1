package com.muesli.music.domain.like;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class LikeCommand {

    @Getter
    @Builder
    @ToString
    public static class RegisterLikeRequest {
        private final Long userId;
        private final Long likeableId;
        private final String likeableType;

        public Like toEntity(Long userId) {
            System.out.println("LikeCommand :: toEntity");
            return Like.builder()
                    .userId(userId)
                    .likeableId(likeableId)
                    .likeableType(likeableType)
                    .build();
        }
    }
}
