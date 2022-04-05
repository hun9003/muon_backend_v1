package com.muesli.music.domain.album;

import com.muesli.music.common.exception.BaseException;
import com.muesli.music.common.response.ErrorCode;
import com.muesli.music.domain.artist.ArtistInfo;
import com.muesli.music.domain.like.LikeInfo;
import com.muesli.music.domain.like.LikeReader;
import com.muesli.music.domain.track.TrackReader;
import com.muesli.music.domain.user.UserInfo;
import com.muesli.music.domain.user.token.UsertokenReader;
import com.muesli.music.interfaces.user.PageInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
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
        return new AlbumInfo.Main(album, trackList);
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
     * @param token
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<AlbumInfo.Main> getLikeList(String token, Pageable pageable) {
        System.out.println("LikeServiceImpl :: getLikeAlbumList");
        var usertoken = usertokenReader.getUsertoken(token);
        var userId = usertoken.getUser().getId();
        var albumInfoList = albumReader.getAlbumLikeList(userId).stream().peek(
                main -> {
                    var track = trackReader.getTrackArtist(main.getId());
                    if (track != null) {
                        main.setArtistInfo(new ArtistInfo.Main(track.getTrackArtists().iterator().next().getArtist()));
                    }
                }
        ).collect(Collectors.toList());
        // 페이징
        var pageInfo = new PageInfo(pageable, albumInfoList.size());
        return albumInfoList.subList(pageInfo.getStartNum(), pageInfo.getEndNum());
    }
}
