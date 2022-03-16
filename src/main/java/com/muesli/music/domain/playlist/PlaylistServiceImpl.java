package com.muesli.music.domain.playlist;

import com.muesli.music.common.exception.BaseException;
import com.muesli.music.common.response.ErrorCode;
import com.muesli.music.domain.artist.ArtistInfo;
import com.muesli.music.domain.like.LikeInfo;
import com.muesli.music.domain.like.LikeReader;
import com.muesli.music.domain.track.TrackInfo;
import com.muesli.music.domain.user.UserInfo;
import com.muesli.music.domain.user.UserReader;
import com.muesli.music.interfaces.user.PageInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlaylistServiceImpl implements PlaylistService{
    private final PlaylistReader playlistReader;
    private final PlaylistStore playlistStore;
    private final LikeReader likeReader;
    private final UserReader userReader;

    /**
     * 플레이리스트 조회
     * @param playlistId
     * @param userInfo
     * @return
     */
    @Override
    @Transactional
    public PlaylistInfo.Main findPlaylistInfo(Long playlistId, UserInfo.Main userInfo, Pageable pageable) {
        System.out.println("PlaylistServiceImpl :: findPlaylistInfo");
        var playlist = playlistReader.getPlaylistBy(playlistId);
        playlist.setViews(playlist.getViews());
        var trackInfoList = playlist.playlistTrackList.stream().map(
                playlistTrack -> {
                    var track = playlistTrack.getTrack();
                    if (track.getTrackArtists().iterator().next().getArtist() == null) return null;
                    var artistInfo = new ArtistInfo.Main(track.getTrackArtists().iterator().next().getArtist());
                    var likeInfo = new LikeInfo.Main(likeReader.getLikeBy(userInfo.getId(), track.getId(), "App\\Track"));
                    likeInfo.setLikeCount((long) track.getLikeList().size());
                    var trackInfo = new TrackInfo.Main(track, artistInfo);
                    trackInfo.setLikeInfo(likeInfo);
                    return trackInfo;
                }
        ).filter(Objects::nonNull).collect(Collectors.toList());
        var playlistLikeCount = (long) playlist.getLikeList().size();
        var playlistLikeInfo = new LikeInfo.Main(likeReader.getLikeBy(userInfo.getId(), playlist.getId(), "App\\Playlist"), playlistLikeCount);
        var playlistUserInfo = new UserInfo.Main(userReader.getUser(playlist.getUserId()));

        // 페이징
        var trackCount = trackInfoList.size();
        var pageInfo = new PageInfo(pageable, trackCount);

        trackInfoList = trackInfoList.subList(pageInfo.getStartNum(), pageInfo.getEndNum());
        var playlistInfo = new PlaylistInfo.Main(playlist, playlistUserInfo, trackInfoList, playlistLikeInfo);
        playlistInfo.setTrackCount(trackCount);
        return playlistInfo;
    }

    /**
     * 내 플레이리스트 조회
     * @param userInfo
     * @return
     */
    @Override
    @Transactional
    public List<PlaylistInfo.Main> findPlaylistInfoMyList(UserInfo.Main userInfo) {
        System.out.println("PlaylistServiceImpl :: findPlaylistInfoMyList");
        return null;
    }

    /**
     * 플레이 리스트 등록
     * @param command
     * @param userInfo
     * @return
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
     * @param command
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
            throw new BaseException(ErrorCode.COMMON_PERMISSION_FALE);
        }
    }

    /**
     * 플레이 리스트 삭제
     * @param playlistId
     * @param userInfo
     */
    @Override
    @Transactional
    public void removePlaylist(Long playlistId, UserInfo.Main userInfo) {
        System.out.println("PlaylistServiceImpl :: removePlaylist");
        var playlist = playlistReader.getPlaylistBy(playlistId);
        if (Objects.equals(userInfo.getId(), playlist.getUserId())) {
            playlistStore.delete(playlist);
        } else {
            throw new BaseException(ErrorCode.COMMON_PERMISSION_FALE);
        }
    }

    /**
     * 좋아하는 플레이리스트
     * @param likeableType
     * @param userInfo
     * @return
     */
    @Override
    @Transactional
    public List<PlaylistInfo.Main> getLikeList(String likeableType, UserInfo.Main userInfo) {
        System.out.println("PlaylistServiceImpl :: getLikeList");
        return null;
    }

    /**
     * 플레이리스트에 트랙 추가
     * @param command
     */
    @Override
    @Transactional
    public void addTrackToPlaylist(PlaylistCommand.TrackToPlaylistRequest command, UserInfo.Main userInfo) {
        System.out.println("PlaylistServiceImpl :: addTrackToPlaylist");

    }

    /**
     * 플레이리스트에 트랙 삭제
     * @param command
     * @param userInfo
     */
    @Override
    @Transactional
    public void removeTrackToPlaylist(PlaylistCommand.TrackToPlaylistRequest command, UserInfo.Main userInfo) {
        System.out.println("PlaylistServiceImpl :: removeTrackToPlaylist");
    }
}
