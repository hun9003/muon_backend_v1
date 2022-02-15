package com.muesli.music.infrastructure.like;

import com.muesli.music.common.exception.InvalidParamException;
import com.muesli.music.domain.like.Like;
import com.muesli.music.domain.like.LikeStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class LikeStoreImpl implements LikeStore {
    private final LikeRepository likeRepository;

    @Override
    public Like store(Like initLike) {
        System.out.println("LikeStoreImpl :: store");
        if (initLike.getUserId() == null) throw new InvalidParamException("Like.UserId");
        return likeRepository.save(initLike);
    }
}
