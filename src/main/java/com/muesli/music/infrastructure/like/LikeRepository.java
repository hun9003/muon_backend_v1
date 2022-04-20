package com.muesli.music.infrastructure.like;

import com.muesli.music.domain.like.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    @Query(value = "SELECT l FROM Like l WHERE l.likeableId = :likeableId AND l.likeableType LIKE CONCAT('%', :likeableType, '%') AND l.userId = :userId")
    Optional<Like> findByUserIdAndLikeableIdAndLikeableType(Long userId, Long likeableId, String likeableType);

    @Query(value = "SELECT COUNT(l) FROM Like l WHERE l.likeableId = :likeableId AND l.likeableType LIKE CONCAT('%', :likeableType, '%') AND l.isLike = 1")
    Optional<Long> countByLike(Long likeableId, String likeableType);
}
