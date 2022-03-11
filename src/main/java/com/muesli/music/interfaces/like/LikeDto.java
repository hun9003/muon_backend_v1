package com.muesli.music.interfaces.like;


import com.muesli.music.domain.like.LikeCommand;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.List;

public class LikeDto {

    @Getter
    @Builder
    @ToString
    public static class LikeInfo {
        private final Long id;
        private final int isLike;
        private final ZonedDateTime createdAt;
        private final Long likeCount;
    }

    @Getter
    @Builder
    @ToString
    public static class LikeItemInfo {
        private final Long id;
        private final ZonedDateTime createdAt;
    }

    @Getter
    @Builder
    @ToString
    public static class LikeInfoList {
        private final List<LikeItemInfo> likeInfoList;
    }

    @Getter
    @Builder
    @ToString
    public static class RegisterLike {
        @NotNull(message = "대상 id(likeableId)은 필수값입니다.")
        private final Long likeableId;

        @NotBlank
        @NotEmpty(message = "타입(likeableType)은 필수값입니다.")
        private final String likeableType;

        private final Long userId;

        public LikeCommand.RegisterLikeRequest toCommand() {
            return LikeCommand.RegisterLikeRequest.builder()
                    .likeableType(likeableType)
                    .likeableId(likeableId)
                    .build();
        }
    }
}
