package com.muesli.music.infrastructure.like;

import com.muesli.music.domain.like.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findLikeBy(Long userId, Long likeableId, String likeableType);
}
