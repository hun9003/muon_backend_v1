package com.muesli.music.domain.playlog;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class PlaylogCommand {

    @Getter
    @ToString
    @Builder
    public static class RegisterPlayLog {
        private Long trackId;
        private Long albumId;
        private Long userId;
        private String useragent;
        private String ip;

        public Playlog toEntity() {
            return Playlog.builder()
                    .trackId(trackId)
                    .albumId(albumId)
                    .userId(userId)
                    .useragent(useragent)
                    .ip(ip)
                    .build();
        }
    }
}
