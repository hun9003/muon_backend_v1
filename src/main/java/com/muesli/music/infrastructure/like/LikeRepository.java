package com.muesli.music.infrastructure.like;

import com.muesli.music.domain.album.Album;
import com.muesli.music.domain.artist.Artist;
import com.muesli.music.domain.like.Like;
import com.muesli.music.domain.track.Track;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    Optional<Like> findByUserIdAndLikeableIdAndLikeableType(Long userId, Long likeableId, String likeableType);

    Optional<Like> findByUserIdAndTrackAndLikeableType(Long userId, Track track, String likeableType);

    Optional<Like> findByUserIdAndAlbumAndLikeableType(Long userId, Album album, String likeableType);

    Optional<Like> findByUserIdAndArtistAndLikeableType(Long userId, Artist artist, String likeableType);

    Optional<Long> countByTrackAndLikeableType(Track track, String likeableType);

    Optional<Long> countByAlbumAndLikeableType(Album album, String likeableType);

    Optional<Long> countByArtistAndLikeableType(Artist artist, String likeableType);
}
