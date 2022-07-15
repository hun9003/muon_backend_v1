package com.muesli.music.interfaces.player;

import com.muesli.music.domain.playlog.PlaylogCommand;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class PlayerDto {
    @Getter
    @Setter
    @ToString
    public static class RegisterPlayLog {
        private Long trackId;
        private Long albumId;
        private Long userId;
        private String useragent;
        private String ip;

        public RegisterPlayLog(Long trackId, String useragent, String ip) {
            this.trackId = trackId;
            this.useragent = useragent;
            this.ip = ip;
        }

        public PlaylogCommand.RegisterPlayLog toCommand(Long albumId, Long userId) {
            return PlaylogCommand.RegisterPlayLog.builder()
                    .trackId(trackId)
                    .albumId(albumId)
                    .userId(userId)
                    .useragent(useragent)
                    .ip(ip)
                    .build();
        }
    }
}
