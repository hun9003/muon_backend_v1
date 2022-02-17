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
        private final Track track;
        private final Album album;
        private final Artist artist;

        public Like toEntity(Long userId) {
            System.out.println("LikeCommand :: toEntity");
            var like = Like.builder()
                    .userId(userId)
                    .likeableType(likeableType);

            switch (likeableType) {
                case "App\\Track": like.item(track);break;
                case "App\\Album": like.item(album);break;
                case "App\\Artist": like.item(artist);break;
            }
            return like.build();
        }
    }
}
