package com.muesli.music.domain.like;

public interface LikeReader {
    Like getLikeBy(Long userId, Long likeableId, String likeableType);

    Like getLikyBy(Long likeId);
}
