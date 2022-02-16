package com.muesli.music.domain.artist;

import com.muesli.music.domain.like.LikeInfo;
import com.muesli.music.domain.like.LikeReader;
import com.muesli.music.domain.user.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArtistServiceImpl implements ArtistService{
    private final ArtistReader artistReader;
    private final LikeReader likeReader;

    /**
     * 아티스트 정보 가져오기
     * @param artistId
     * @param userInfo
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public ArtistInfo.Main findArtistInfo(Long artistId, UserInfo.Main userInfo) {
        System.out.println("ArtistServiceImpl :: findArtistInfo");
        var artist = artistReader.getArtistBy(artistId);
        var albumList = artistReader.getAlbumList(artist);

        albumList.forEach(
                albumBasicInfo -> {
                    var albumLikeInfo = new LikeInfo(likeReader.getLikeBy(userInfo.getId(), albumBasicInfo.getId(), "App\\Album"));
                    albumBasicInfo.setLikeInfo(albumLikeInfo);
                }
        );

        var artistLikecount = likeReader.getLikeCount(artist.getId(), "App\\Artist");
        var artistLikeInfo = new LikeInfo(likeReader.getLikeBy(userInfo.getId(), artist.getId(), "App\\Artist"), artistLikecount);
        return new ArtistInfo.Main(artist, albumList, artistLikeInfo);
    }

    /**
     * 아티스트 좋아요 정보 가져오기
     * @param userInfo
     * @param artistInfo
     * @return
     */
    @Override
    public LikeInfo findLikeBy(UserInfo.Main userInfo, ArtistInfo.Main artistInfo) {
        System.out.println("ArtistServiceImpl :: findLikeBy");
        return new LikeInfo(likeReader.getLikeBy(userInfo.getId(), artistInfo.getId(), "App\\Artist"));
    }
}
