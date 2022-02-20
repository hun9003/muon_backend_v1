package com.muesli.music.infrastructure.like;

import com.muesli.music.domain.like.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUserIdAndLikeableIdAndLikeableType(Long userId, Long likeableId, String likeableType);

    Optional<Long> countByLikeableIdAndLikeableType(Long likeableId, String likeableType);
}
