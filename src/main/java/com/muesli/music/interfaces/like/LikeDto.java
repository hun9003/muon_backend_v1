package com.muesli.music.interfaces.like;


import com.muesli.music.domain.like.LikeCommand;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LikeDto {

    @Getter
    @Builder
    @ToString
    public static class LikeInfo {
        private final Long id;
        private final Long likeableId;
        private final String likeableType;
        private final int isLike;
        private final LocalDate createdAt;
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
        private final String ids;

        public LikeItemInfoList(String type, String ids) {
            this.type = type;
            this.ids = ids;
        }

        public LikeCommand.ShowLikeListRequest toCommand() {
            List<String> idsStr = new ArrayList<>(Arrays.asList(ids.split(",")));
            List<Long> idsLong = idsStr.stream().map(Long::parseLong).collect(Collectors.toList());
            return LikeCommand.ShowLikeListRequest.builder()
                    .type(type)
                    .ids(idsLong)
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
