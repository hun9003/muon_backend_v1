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
        private final Long likeableId;
        private final int isLike;
        private final ZonedDateTime createdAt;
        private final Long likeCount;
    }

    @Getter
    @Builder
    @ToString
    public static class LikeInfoList {
        private final String type;
        private final List<LikeInfo> likeInfoList;

        public LikeInfoList(String type, List<LikeInfo> likeInfoList) {
            this.type = type;
            this.likeInfoList = likeInfoList;
        }
    }

    @Getter
    @Builder
    @ToString
    public static class LikeItemInfoList {
        private final String type;
        private final List<Long> ids;

        public LikeCommand.ShowLikeListRequest toCommand() {
            return LikeCommand.ShowLikeListRequest.builder()
                    .type(type)
                    .ids(ids)
                    .build();
        }
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
