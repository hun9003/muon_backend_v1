package com.muesli.music.domain.playlist;

import com.muesli.music.common.exception.BaseException;
import com.muesli.music.common.response.ErrorCode;
import com.muesli.music.common.util.ItemGenerator;
import com.muesli.music.domain.artist.ArtistInfo;
import com.muesli.music.domain.playlist.track.PlaylistTrack;
import com.muesli.music.domain.track.TrackInfo;
import com.muesli.music.domain.track.TrackReader;
import com.muesli.music.domain.user.UserInfo;
import com.muesli.music.domain.user.UserReader;
import com.muesli.music.domain.user.token.UsertokenReader;
import com.muesli.music.interfaces.user.PageInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlaylistServiceImpl implements PlaylistService{
    private final PlaylistReader playlistReader;
    private final PlaylistStore playlistStore;
    private final UserReader userReader;
    private final TrackReader trackReader;
    private final UsertokenReader usertokenReader;

    /**
     * 플레이리스트 조회
     * @param playlistId 플레이리스트 idx
     * @param pageable 페이징 처리를 위한 객체
     * @param userInfo 유저 정보
     * @return 플레이리스트 정보
     */
    @Override
    @Transactional
    public PlaylistInfo.Main findPlaylistInfo(Long playlistId, Pageable pageable, UserInfo.Main userInfo) {
        System.out.println("PlaylistServiceImpl :: findPlaylistInfo");
        var playlist = playlistReader.getPlaylistBy(playlistId);

        if (playlist.getIsPublic() == 0) {
            if (!Objects.equals(playlist.getUserId(), userInfo.getId())) throw new BaseException(ErrorCode.USER_PERMISSION_FALE);
        }

        playlist.setViews(playlist.getViews());
        var trackInfoList = playlist.playlistTrackList.stream().map(
                playlistTrack -> {
                    var track = playlistTrack.getTrack();
                    if (track.getTrackArtists().iterator().next().getArtist() == null) return null;
                    var artistInfo = new ArtistInfo.Main(track.getTrackArtists().iterator().next().getArtist());
                    return new TrackInfo.Main(track, artistInfo);
                }
        ).filter(Objects::nonNull).collect(Collectors.toList());

        var playlistUserInfo = new UserInfo.Main(userReader.getUser(playlist.getUserId()));

        // 페이징
        var trackCount = trackInfoList.size();
        var pageInfo = new PageInfo(pageable, trackCount);
        trackInfoList = trackInfoList.subList(pageInfo.getStartNum(), pageInfo.getEndNum());

        var playlistInfo = new PlaylistInfo.Main(playlist, playlistUserInfo, trackInfoList);
        playlistInfo.setTrackCount(trackCount);
        return playlistInfo;
    }

    @Override
    public PlaylistInfo.Main2 findPlaylistInfo2(Long playlistId, Pageable pageable, UserInfo.Main userInfo) {
        var playlist = playlistReader.getPlaylistBy2(playlistId);
        if (playlist.getIsPublic() == 0) {
            if (!Objects.equals(playlist.getUserId(), userInfo.getId())) throw new BaseException(ErrorCode.USER_PERMISSION_FALE);
        }
        playlist.setViews(playlist.getViews());

        var trackCount = trackReader.getPlaylistTrackCount(playlistId);
        var pageInfo = new PageInfo(pageable, trackCount);
        var trackList = trackReader.getPlaylistTrackList(playlistId, pageInfo.getStartNum(), pageInfo.getEndNum());
        var newTrackList = ItemGenerator.makeItemListMap(trackList);
        var trackListInfo = newTrackList.stream().map(TrackInfo.TrackListInfo::new).collect(Collectors.toList());

        var playlistUserInfo = new UserInfo.Main(userReader.getUser(playlist.getUserId()));
        var playlistInfo = new PlaylistInfo.Main2(playlist, playlistUserInfo, trackListInfo);
        playlistInfo.setTrackCount(trackCount);
        return playlistInfo;
    }

    /**
     * 내 플레이리스트 조회
     * @param userInfo 유저 정보
     * @param pageable 페이징 처리를 위한 객체
     * @return 플레이리스트 정보 리스트
     */
    @Override
    @Transactional
    public List<PlaylistInfo.Main> findPlaylistInfoMyList(UserInfo.Main userInfo, Pageable pageable) {
        System.out.println("PlaylistServiceImpl :: findPlaylistInfoMyList");
        var playlistInfoList = playlistReader.getPlaylistList(userInfo);

        // 페이징
        var pageInfo = new PageInfo(pageable, playlistInfoList.size());
        return playlistInfoList.subList(pageInfo.getStartNum(), pageInfo.getEndNum());
    }
    @Override
    public List<PlaylistInfo.PlayListInfo> findPlaylistInfoMyList2(UserInfo.Main userInfo, Pageable pageable) {
        System.out.println("PlaylistServiceImpl :: findPlaylistInfoMyList");
        var pageInfo = new PageInfo(pageable, playlistReader.getPlaylistListCount(userInfo.getId()));
        var playlistList = playlistReader.getPlaylistList(userInfo.getId(), pageInfo.getStartNum(), pageInfo.getEndNum());
        var newPlaylist = ItemGenerator.makeItemListMap(playlistList);

        return newPlaylist.stream().map(PlaylistInfo.PlayListInfo::new).collect(Collectors.toList());
    }

    /**
     * 플레이 리스트 등록
     * @param command 플레이리스트 저장을 위한 데이터 객체
     * @param userInfo 유저 정보
     * @return 플레이리스트 정보
     */
    @Override
    @Transactional
    public PlaylistInfo.Main registerPlaylist(PlaylistCommand.RegisterPlaylistRequest command, UserInfo.Main userInfo) {
        System.out.println("PlaylistServiceImpl :: registerPlaylist");
        var initPlaylist = command.toEntity(userInfo.getId());
        var playlist = playlistStore.store(initPlaylist);
        return new PlaylistInfo.Main(playlist, userInfo);
    }

    /**
     * 플레이 리스트 수정
     * @param command 플레이리스트 수정을 위한 데이터 객체
     * @param userInfo 유저 정보
     */
    @Override
    @Transactional
    public void updatePlaylist(PlaylistCommand.UpdatePlaylistRequest command, UserInfo.Main userInfo) {
        System.out.println("PlaylistServiceImpl :: updatePlaylist");
        var initPlaylist = command.toEntity(userInfo.getId());
        var playlist = playlistReader.getPlaylistBy(initPlaylist.getId());

        if (Objects.equals(userInfo.getId(), playlist.getUserId())) {
            playlist.setPlaylist(initPlaylist);
        } else {
            throw new BaseException(ErrorCode.USER_PERMISSION_FALE);
        }
    }

    /**
     * 플레이 리스트 삭제
     * @param playlistId 플레이리스트 idx
     * @param userInfo 유저 정보
     */
    @Override
    @Transactional
    public void removePlaylist(Long playlistId, UserInfo.Main userInfo) {
        System.out.println("PlaylistServiceImpl :: removePlaylist");
        var playlist = playlistReader.getPlaylistBy(playlistId);
        if (Objects.equals(userInfo.getId(), playlist.getUserId())) {
            playlistStore.deletePlaylistTrack(playlistId);
            playlistStore.delete(playlist);
        } else {
            throw new BaseException(ErrorCode.USER_PERMISSION_FALE);
        }
    }

    /**
     * 좋아하는 플레이리스트
     * @param token 유저 토큰
     * @param pageable 페이징 처리를 위한 객체
     * @return 플레이리스트 정보 리스트
     */
    @Override
    @Transactional
    public List<PlaylistInfo.Main> getLikeList(String token, Pageable pageable) {
        System.out.println("PlaylistServiceImpl :: getLikeList");
        var usertoken = usertokenReader.getUsertoken(token);
        if(usertoken.getUser() == null) throw new BaseException(ErrorCode.USER_PERMISSION_FALE);
        var userInfo = new UserInfo.Main(usertoken.getUser());
        var playlistInfoList =  playlistReader.getPlaylistLikeList(userInfo);
        // 페이징
        var pageInfo = new PageInfo(pageable, playlistInfoList.size());
        return playlistInfoList.subList(pageInfo.getStartNum(), pageInfo.getEndNum());
    }
    @Override
    public List<PlaylistInfo.PlayListInfo> getLikeList2(UserInfo.Main userInfo, Pageable pageable) {
        System.out.println("PlaylistServiceImpl :: getLikeList");
        if(userInfo == null) throw new BaseException(ErrorCode.USER_PERMISSION_FALE);
        var pageInfo = new PageInfo(pageable, playlistReader.getPlaylistLikeListCount(userInfo.getId()));
        var playlistList =  playlistReader.getPlaylistLikeList(userInfo.getId(), pageInfo.getStartNum(), pageInfo.getEndNum());
        var newPlaylistList = ItemGenerator.makeItemListMap(playlistList);
        return newPlaylistList.stream().map(PlaylistInfo.PlayListInfo::new).collect(Collectors.toList());
    }

    /**
     * 플레이리스트에 트랙 추가
     * @param command 플레이리스트에 트랙 리스트 저장을 위한 데이터 객체
     * @param userInfo 유저 정보
     */
    @Override
    @Transactional
    public void addTrackToPlaylist(PlaylistCommand.TrackToPlaylistRequest command, UserInfo.Main userInfo) {
        System.out.println("PlaylistServiceImpl :: addTrackToPlaylist");
        var playlist = playlistReader.getPlaylistBy(command.getPlaylistId());

        // 사용자 유효성 체크
        if (!Objects.equals(userInfo.getId(), playlist.getUserId())) {
            throw new BaseException(ErrorCode.USER_PERMISSION_FALE);
        }

        var trackList = playlist.getPlaylistTrackList();
        var lastPosition = trackList.size();
        var initTrackList = command.getTrackList();

        // 트랙 중복 제거
        var trackIdList = trackList.stream().map(playlistTrack -> playlistTrack.getTrack().getId()).collect(Collectors.toList());

        initTrackList.removeAll(trackIdList);

        for (var i = 0; i < initTrackList.size(); i++) {
            var track = trackReader.getTrackBy(initTrackList.get(i));
            var initPlayTrack = command.toEntity(playlist, track, lastPosition+i+1);
            playlistStore.store(initPlayTrack);
        }

    }

    /**
     * 플레이리스트에 트랙 삭제
     * @param command 플레이리스트 트랙리스트 삭제를 위한 데이터 객체
     * @param userInfo 유저 정보
     */
    @Override
    @Transactional
    public void removeTrackToPlaylist(PlaylistCommand.TrackToPlaylistRequest command, UserInfo.Main userInfo) {
        System.out.println("PlaylistServiceImpl :: removeTrackToPlaylist");
        var playlist = playlistReader.getPlaylistBy(command.getPlaylistId());

        // 사용자 유효성 체크
        if (!Objects.equals(userInfo.getId(), playlist.getUserId())) {
            throw new BaseException(ErrorCode.USER_PERMISSION_FALE);
        }

        var initTrackList = command.getTrackList();
        for (Long trackId : initTrackList) {
            var track = trackReader.getTrackBy(trackId);
            var playlistTrack = playlistReader.getPlaylistTrack(playlist.getId(), track.getId());
            playlistStore.delete(playlistTrack);
            playlistStore.updatePosition(playlistTrack);
        }
    }

    /**
     * 플레이리스트의 트랙 조회
     * @param playlistId 플레이리스트 idx
     * @param userInfo 유저 정ㅂ
     * @return 플레이리스트 트랙 리스트
     */
    @Transactional
    @Override
    public List<PlaylistTrack> getTrackToPlaylist(Long playlistId, UserInfo.Main userInfo) {
        System.out.println("PlaylistServiceImpl :: getTrackToPlaylist");
        var playlist = playlistReader.getPlaylistBy(playlistId);

        // 사용자 유효성 체크
        if (!Objects.equals(userInfo.getId(), playlist.getUserId())) {
            throw new BaseException(ErrorCode.USER_PERMISSION_FALE);
        }

        return playlist.getPlaylistTrackList();
    }
}
