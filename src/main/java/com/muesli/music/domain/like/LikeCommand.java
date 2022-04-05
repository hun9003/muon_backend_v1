package com.muesli.music.domain.like;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

public class LikeCommand {

    @Getter
    @Builder
    @ToString
    public static class RegisterLikeRequest {
        private final Long userId;
        private final String likeableType;
        private final Long likeableId;

        public Like toEntity(Long userId) {
            System.out.println("LikeCommand :: toEntity");
            return Like.builder()
                    .userId(userId)
                    .likeableId(likeableId)
                    .likeableType(likeableType)
                    .build();
        }
    }

    @Getter
    @Builder
    @ToString
    public static class ShowLikeListRequest {
        private final String type;
        private final List<Long> ids;
    }
}
