package com.muesli.music.domain.playlist;

import com.muesli.music.common.exception.BaseException;
import com.muesli.music.common.response.ErrorCode;
import com.muesli.music.domain.user.token.UsertokenReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlaylistServiceImpl implements PlaylistService{
    private final UsertokenReader usertokenReader;
    private final PlaylistReader playlistReader;
    private final PlaylistStore playlistStore;

    /**
     * 플레이리스트 조회
     * @param playlistId
     * @param token
     * @return
     */
    @Override
    @Transactional
    public PlaylistInfo.Main findPlaylistInfo(Long playlistId, String token) {
        System.out.println("PlaylistServiceImpl :: findPlaylistInfo");
        return null;
    }

    /**
     * 내 플레이리스트 조회
     * @param token
     * @return
     */
    @Override
    @Transactional
    public List<PlaylistInfo.Main> findPlaylistInfoMyList(String token) {
        System.out.println("PlaylistServiceImpl :: findPlaylistInfoMyList");
        return null;
    }

    /**
     * 플레이 리스트 등록
     * @param command
     * @return
     */
    @Override
    @Transactional
    public PlaylistInfo.Main registerPlaylist(PlaylistCommand.RegisterPlaylistRequest command, String token) {
        System.out.println("PlaylistServiceImpl :: registerPlaylist");
        var usertoken = usertokenReader.getUsertoken(token);
        if(usertoken.getUser() == null) throw new BaseException(ErrorCode.COMMON_PERMISSION_FALE);
        var initPlaylist = command.toEntity(usertoken.getUser().getId());
        var playlist = playlistStore.store(initPlaylist);
//        var trackInfoList = playlist.playlistTrackList.stream().map(
//                playlistTrack -> {
//                    var track = playlistTrack.getTrack();
//                    var artistInfo = new ArtistInfo.Main(track.getTrackArtist().getArtist());
//                    var likeInfo = new LikeInfo.Main(new Like());
//                    likeInfo.setLikeCount((long) track.getLikeList().size());
//                    var trackInfo = new TrackInfo.Main(track, artistInfo);
//                    trackInfo.setLikeInfo(likeInfo);
//                    return trackInfo;
//                }
//        ).collect(Collectors.toList());
        return new PlaylistInfo.Main(playlist);
    }

    /**
     * 플레이 리스트 수정
     * @param command
     */
    @Override
    @Transactional
    public void updatePlaylist(PlaylistCommand.UpdatePlaylistRequest command, String token) {
        System.out.println("PlaylistServiceImpl :: updatePlaylist");
        var usertoken = usertokenReader.getUsertoken(token);
        if(usertoken.getUser() == null) throw new BaseException(ErrorCode.COMMON_PERMISSION_FALE);
        var initPlaylist = command.toEntity(usertoken.getUser().getId());
        var playlist = playlistReader.getPlaylistBy(initPlaylist.getId());
        if (Objects.equals(usertoken.getUser().getId(), playlist.getUserId())) {
            playlist.setPlaylist(initPlaylist);
        } else {
            throw new BaseException(ErrorCode.COMMON_PERMISSION_FALE);
        }
    }

    /**
     * 플레이 리스트 삭제
     * @param playlistId
     * @param token
     */
    @Override
    @Transactional
    public void removePlaylist(Long playlistId, String token) {
        System.out.println("PlaylistServiceImpl :: removePlaylist");
        var usertoken = usertokenReader.getUsertoken(token);
        if(usertoken.getUser() == null) throw new BaseException(ErrorCode.COMMON_PERMISSION_FALE);
        var playlist = playlistReader.getPlaylistBy(playlistId);
        if (Objects.equals(usertoken.getUser().getId(), playlist.getUserId())) {
            playlistStore.delete(playlist);
        } else {
            throw new BaseException(ErrorCode.COMMON_PERMISSION_FALE);
        }
    }

    /**
     * 좋아하는 플레이리스트
     * @param likeableType
     * @param token
     * @return
     */
    @Override
    @Transactional
    public List<PlaylistInfo.Main> getLikeList(String likeableType, String token) {
        System.out.println("PlaylistServiceImpl :: getLikeList");
        return null;
    }

    /**
     * 플레이리스트에 트랙 추가
     * @param command
     */
    @Override
    @Transactional
    public void addTrackToPlaylist(PlaylistCommand.TrackToPlaylistRequest command, String token) {
        System.out.println("PlaylistServiceImpl :: addTrackToPlaylist");
    }

    /**
     * 플레이리스트에 트랙 삭제
     * @param command
     * @param token
     */
    @Override
    @Transactional
    public void removeTrackToPlaylist(PlaylistCommand.TrackToPlaylistRequest command, String token) {
        System.out.println("PlaylistServiceImpl :: removeTrackToPlaylist");
    }
}
