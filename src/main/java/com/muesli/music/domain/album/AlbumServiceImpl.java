package com.muesli.music.domain.album;

import com.muesli.music.domain.like.LikeReader;
import com.muesli.music.domain.user.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlbumServiceImpl implements AlbumService {
    private final AlbumReader albumReader;
    private final LikeReader likeReader;

    @Override
    @Transactional(readOnly = true)
    public AlbumInfo.Main findAlbumInfo(Long albumId, UserInfo.Main userInfo) {
        System.out.println("AlbumServiceImpl :: findAlbumInfo");
        var album = albumReader.getAlbumBy(albumId);
        var trackList = albumReader.getTrackList(album);

        trackList.forEach(
                trackInfo -> {
                    var trackLikeInfo = new AlbumInfo.LikeInfo(likeReader.getLikeBy(userInfo.getId(), trackInfo.getId(), "App\\Track"));
                    trackInfo.setLikeInfo(trackLikeInfo);
                }
        );

        var albumLikeInfo = new AlbumInfo.LikeInfo(likeReader.getLikeBy(userInfo.getId(), album.getId(), "App\\Album"));
        return new AlbumInfo.Main(album, trackList, albumLikeInfo);
    }

    @Override
    public AlbumInfo.LikeInfo findLikeBy(UserInfo.Main userInfo, AlbumInfo.Main albumInfo) {
        System.out.println("AlbumServiceImpl :: findLikeBy");
        return new AlbumInfo.LikeInfo(likeReader.getLikeBy(userInfo.getId(), albumInfo.getId(), "App\\Album"));
    }
}
