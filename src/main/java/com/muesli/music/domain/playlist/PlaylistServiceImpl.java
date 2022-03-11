package com.muesli.music.domain.playlist;

import com.muesli.music.domain.user.token.UsertokenReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

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
     * @param usertoken
     * @return
     */
    @Override
    @Transactional
    public PlaylistInfo.Main findPlaylistInfo(Long playlistId, String usertoken) {
        System.out.println("PlaylistServiceImpl :: findPlaylistInfo");
        return null;
    }

    /**
     * 내 플레이리스트 조회
     * @param usertoken
     * @return
     */
    @Override
    @Transactional
    public List<PlaylistInfo.Main> findPlaylistInfoMyList(String usertoken) {
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
    public PlaylistInfo.Main registerPlaylist(PlaylistCommand.RegisterPlaylistRequest command, String usertoken) {
        System.out.println("PlaylistServiceImpl :: registerPlaylist");
        var usertokenInfo = usertokenReader.getUsertoken(usertoken);
        var initPlaylist = command.toEntity(usertokenInfo.getUser().getId());
        var playlist = playlistStore.store(initPlaylist);
        return new PlaylistInfo.Main(playlist, new PlaylistInfo., null);
    }

    /**
     * 플레이 리스트 수정
     * @param command
     * @return
     */
    @Override
    @Transactional
    public PlaylistInfo.Main updatePlaylist(PlaylistCommand.RegisterPlaylistRequest command, String usertoken) {
        System.out.println("PlaylistServiceImpl :: updatePlaylist");
        return null;
    }

    /**
     * 플레이 리스트 삭제
     * @param playlistId
     * @param usertoken
     */
    @Override
    @Transactional
    public void removePlaylist(Long playlistId, String usertoken) {
        System.out.println("PlaylistServiceImpl :: removePlaylist");
    }

    /**
     * 좋아하는 플레이리스트
     * @param likeableType
     * @param usertoken
     * @return
     */
    @Override
    @Transactional
    public List<PlaylistInfo.Main> getLikeList(String likeableType, String usertoken) {
        System.out.println("PlaylistServiceImpl :: getLikeList");
        return null;
    }

    /**
     * 플레이리스트에 트랙 추가
     * @param command
     */
    @Override
    @Transactional
    public void addTrackToPlaylist(PlaylistCommand.TrackToPlaylistRequest command, String usertoken) {
        System.out.println("PlaylistServiceImpl :: addTrackToPlaylist");
    }

    /**
     * 플레이리스트에 트랙 삭제
     * @param command
     */
    @Override
    @Transactional
    public void removeTrackToPlaylist(PlaylistCommand.TrackToPlaylistRequest command, String usertoken) {
        System.out.println("PlaylistServiceImpl :: removeTrackToPlaylist");
    }
}
