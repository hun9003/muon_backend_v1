package com.muesli.music.infrastructure.like;

import com.muesli.music.common.exception.InvalidParamException;
import com.muesli.music.domain.album.Album;
import com.muesli.music.domain.artist.Artist;
import com.muesli.music.domain.like.Like;
import com.muesli.music.domain.like.LikeInfo;
import com.muesli.music.domain.like.LikeReader;
import com.muesli.music.domain.track.Track;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class LikeReaderImpl implements LikeReader {
    private final LikeRepository likeRepository;

    @Override
    public Like getLikeBy(Long userId, Long likeableId, String likeableType) {
        System.out.println("LikeReaderImpl :: getLikeBy");
        Like like = null;
        System.out.println(likeableType);
        switch (likeableType) {
            case "App\\Track":
                var track = new Track(likeableId);
                like = likeRepository.findByUserIdAndTrackAndLikeableType(userId, track, likeableType).orElse(new Like());
                break;
            case "App\\Album":
                var album = new Album(likeableId);
                like = likeRepository.findByUserIdAndAlbumAndLikeableType(userId, album, likeableType).orElse(new Like());
                break;
            case "App\\Artist": ;
                var artist = new Artist(likeableId);
                like = likeRepository.findByUserIdAndArtistAndLikeableType(userId, artist, likeableType).orElse(new Like());
                break;
        }
//        return likeRepository.findByUserIdAndLikeableIdAndLikeableType(userId, likeableId, likeableType).orElse(new Like());

        return like;
    }

    @Override
    public Like getLikeBy(Long likeId) {
        System.out.println("LikeReaderImpl :: getLikeBy");
        return likeRepository.findById(likeId)
                .orElseThrow(InvalidParamException::new);
    }

    @Override
    public List<LikeInfo.Main> getLikeList(String likeableType, Long userId) {
        System.out.println("LikeReaderImpl :: getLikeList");
//        var likeList = likeRepository.findLikeByLikeableTypeAndUserId(likeableType, userId)
//                .orElseThrow(EntityNotFoundException::new);
//        return likeList.stream()
//                .map(LikeInfo.Main::new).collect(Collectors.toList());
        return null;
    }

    @Override
    public Long getLikeCount(Long likeableId, String likeableType) {
        System.out.println("LikeReaderImpl :: getLikeCount");
        Long likecount = 0L;
        switch (likeableType) {
            case "App\\Track":
                var track = new Track(likeableId);
                likecount = likeRepository.countByTrackAndLikeableType(track, likeableType).orElse(0L);
                break;
            case "App\\Album":
                var album = new Album(likeableId);
                likecount = likeRepository.countByAlbumAndLikeableType(album, likeableType).orElse(0L);
                break;
            case "App\\Artist": ;
                var artist = new Artist(likeableId);
                likecount = likeRepository.countByArtistAndLikeableType(artist, likeableType).orElse(0L);
                break;
        }
        return likecount;
    }
}
