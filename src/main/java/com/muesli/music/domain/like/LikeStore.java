package com.muesli.music.domain.like;

public interface LikeStore {
    // 좋아요 정보 저장
    Like store(Like initLike);
}
