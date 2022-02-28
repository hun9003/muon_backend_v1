package com.muesli.music.infrastructure.track;

import com.muesli.music.domain.track.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface TrackRepository  extends JpaRepository<Track, Long> {

    @Query(value = "SELECT t FROM Track t JOIN Like l ON l.likeableId = t.id WHERE l.likeableType = :likeableType AND l.userId = :userId")
    Optional<List<Track>> findLikeByLikeableTypeAndUserId(String likeableType, Long userId);
}
