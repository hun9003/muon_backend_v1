package com.muesli.music.domain.like;

import com.muesli.music.domain.album.Album;
import com.muesli.music.domain.artist.Artist;
import com.muesli.music.domain.track.Track;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

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
            var like = Like.builder()
                    .userId(userId)
                    .likeableId(likeableId)
                    .likeableType(likeableType);

            return like.build();
        }
    }
}
