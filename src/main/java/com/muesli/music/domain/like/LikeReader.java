package com.muesli.music.domain.like;


public interface LikeReader {
    Like getLikeBy(Long userId, Long likeableId, String likeableType);

    Like getLikeBy(Long likeId);

    Long getLikeCount(Long likeableId, String likeableType);
}
