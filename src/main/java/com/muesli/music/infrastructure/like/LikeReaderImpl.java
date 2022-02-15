package com.muesli.music.infrastructure.like;

import com.muesli.music.common.exception.EntityNotFoundException;
import com.muesli.music.common.exception.InvalidParamException;
import com.muesli.music.domain.like.Like;
import com.muesli.music.domain.like.LikeReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class LikeReaderImpl implements LikeReader {
    private final LikeRepository likeRepository;

    @Override
    public Like getLikeBy(Long userId, Long likeableId, String likeableType) {
        System.out.println("LikeReaderImpl :: getLikeBy");
        return likeRepository.findByUserIdAndLikeableIdAndLikeableType(userId, likeableId, likeableType)
                .orElse(new Like());
    }

    @Override
    public Like getLikyBy(Long likeId) {
        System.out.println("LikeReaderImpl :: getLikeBy");
        return likeRepository.findById(likeId)
                .orElseThrow(InvalidParamException::new);
    }
}
