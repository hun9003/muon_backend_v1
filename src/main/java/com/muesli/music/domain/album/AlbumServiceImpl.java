package com.muesli.music.domain.album;

import com.muesli.music.common.exception.BaseException;
import com.muesli.music.common.response.ErrorCode;
import com.muesli.music.domain.artist.ArtistInfo;
import com.muesli.music.domain.like.LikeInfo;
import com.muesli.music.domain.like.LikeReader;
import com.muesli.music.domain.track.TrackReader;
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
    private final TrackReader trackReader;
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
                    var trackLikeCount = trackInfo.getLikeInfo().getLikeCount();
                    var trackLikeInfo = new LikeInfo.Main(likeReader.getLikeBy(userInfo.getId(), trackInfo.getId(), "App\\Track"), trackLikeCount);
                    trackInfo.setLikeInfo(trackLikeInfo);
                }
        );
        var albumLikeCount = (long) album.getLikeList().size();
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
    @Transactional(readOnly = true)
    public LikeInfo.Main findLikeBy(UserInfo.Main userInfo, AlbumInfo.Main albumInfo) {
        System.out.println("AlbumServiceImpl :: findLikeBy");
        return new LikeInfo.Main(likeReader.getLikeBy(userInfo.getId(), albumInfo.getId(), "App\\Album"));
    }

    /**
     * 좋아요 리스트 조회
     * @param likeableType
     * @param token
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<AlbumInfo.Main> getLikeList(String likeableType, String token) {
        System.out.println("LikeServiceImpl :: getLikeAlbumList");
        var usertoken = usertokenReader.getUsertoken(token);
        if(usertoken.getUser() == null) throw new BaseException(ErrorCode.COMMON_BAD_USERTOKEN);
        var albumInfoList = albumReader.getAlbumLikeList(likeableType, usertoken.getUser().getId());
        return albumInfoList.stream().peek(
                main -> {
                    var track = trackReader.getTrackArtist(main.getId());
                    if (track != null) {
                        main.setArtistInfo(new ArtistInfo.Main(track.getTrackArtists().iterator().next().getArtist()));
                    }
                }
        ).collect(Collectors.toList());
    }
}
