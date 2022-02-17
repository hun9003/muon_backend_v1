package com.muesli.music.infrastructure.like;

import com.muesli.music.domain.like.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUserIdAndLikeableIdAndLikeableType(Long userId, Long likeableId, String likeableType);

    // 좋아요 리스트의 실마리 발견
    @Query(value = "SELECT l FROM Like l JOIN Track t ON l.likeableId = t.id WHERE l.likeableType = :likeableType AND l.userId = :userId")
    Optional<List<Like>> findLikeByLikeableTypeAndUserId(String likeableType, Long userId);

    Optional<Long> countByLikeableIdAndLikeableType(Long likeableId, String likeableType);
}
