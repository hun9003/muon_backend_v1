package com.muesli.music.domain.artist;

import com.muesli.music.common.exception.BaseException;
import com.muesli.music.common.response.ErrorCode;
import com.muesli.music.domain.artist.bios.Bios;
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
public class ArtistServiceImpl implements ArtistService{
    private final ArtistReader artistReader;
    private final LikeReader likeReader;
    private final UsertokenReader usertokenReader;

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
        var bios = artist.getBios() != null ? artist.getBios() : new Bios();
        var biosInfo = new ArtistInfo.BiosInfo(bios);
        albumList.forEach(
                albumBasicInfo -> {
                    var albumLikeCount = albumBasicInfo.getLikeInfo().getLikeCount();
                    var albumLikeInfo = new LikeInfo.Main(likeReader.getLikeBy(userInfo.getId(), albumBasicInfo.getId(), "App\\Album"), albumLikeCount);
                    albumBasicInfo.setLikeInfo(albumLikeInfo);
                }
        );
        var artistLikecount = (long) artist.getLikeList().size();
        var artistLikeInfo = new LikeInfo.Main(likeReader.getLikeBy(userInfo.getId(), artist.getId(), "App\\Artist"), artistLikecount);
        return new ArtistInfo.Main(artist, biosInfo, albumList, artistLikeInfo);
    }

    /**
     * 아티스트 좋아요 정보 가져오기
     * @param userInfo
     * @param artistInfo
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public LikeInfo.Main findLikeBy(UserInfo.Main userInfo, ArtistInfo.Main artistInfo) {
        System.out.println("ArtistServiceImpl :: findLikeBy");
        return new LikeInfo.Main(likeReader.getLikeBy(userInfo.getId(), artistInfo.getId(), "App\\Artist"));
    }

    /**
     * 좋아요 리스트 조회
     * @param likeableType
     * @param token
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<ArtistInfo.Main> getLikeList(String likeableType, String token) {
        System.out.println("LikeServiceImpl :: getLikeArtistList");
        var usertoken = usertokenReader.getUsertoken(token);
        if(usertoken.getUser() == null) throw new BaseException(ErrorCode.COMMON_BAD_USERTOKEN);
        return artistReader.getArtistLikeList(usertoken.getUser().getId());
    }
}
