package com.muesli.music.domain.album;

import com.muesli.music.domain.like.LikeInfo;
import com.muesli.music.domain.like.LikeReader;
import com.muesli.music.domain.user.UserInfo;
import com.muesli.music.domain.user.token.UsertokenReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlbumServiceImpl implements AlbumService {
    private final AlbumReader albumReader;
    private final LikeReader likeReader;
    private final UsertokenReader usertokenReader;

    /**
     * 앨범 정보 가져오기
     * @param albumId
     * @param userInfo
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public AlbumInfo.Main findAlbumInfo(Long albumId, UserInfo.Main userInfo) {
        System.out.println("AlbumServiceImpl :: findAlbumInfo");
        var album = albumReader.getAlbumBy(albumId);
        var trackList = albumReader.getTrackList(album);

        trackList.forEach(
                trackInfo -> {
                    var trackLikeCount = likeReader.getLikeCount(trackInfo.getId(), "App\\Track");
                    var trackLikeInfo = new LikeInfo.Main(likeReader.getLikeBy(userInfo.getId(), trackInfo.getId(), "App\\Track"), trackLikeCount);
                    trackInfo.setLikeInfo(trackLikeInfo);
                }
        );
        var albumLikeCount = likeReader.getLikeCount(album.getId(), "App\\Album");
        var albumLikeInfo = new LikeInfo.Main(likeReader.getLikeBy(userInfo.getId(), album.getId(), "App\\Album"), albumLikeCount);
        return new AlbumInfo.Main(album, trackList, albumLikeInfo);
    }

    /**
     * 앨범 좋아요 정보 가져오기
     * @param userInfo
     * @param albumInfo
     * @return
     */
    @Override
    public LikeInfo.Main findLikeBy(UserInfo.Main userInfo, AlbumInfo.Main albumInfo) {
        System.out.println("AlbumServiceImpl :: findLikeBy");
        return new LikeInfo.Main(likeReader.getLikeBy(userInfo.getId(), albumInfo.getId(), "App\\Album"));
    }

    /**
     * 좋아요 리스트 조회
     * @param likeableType
     * @param usertoken
     * @return
     */
    @Override
    public List<AlbumInfo.Main> getLikeList(String likeableType, String usertoken) {
        System.out.println("LikeServiceImpl :: getLikeAlbumList");
        var user = usertokenReader.getUsertoken(usertoken);
        var albumList = albumReader.getAlbumLikeList(likeableType, user.getUser().getId());
        return albumList.stream().peek(
                main -> main.getLikeInfo().setLikeCount(likeReader.getLikeCount(main.getId(), likeableType))
        ).collect(Collectors.toList());
    }
}
