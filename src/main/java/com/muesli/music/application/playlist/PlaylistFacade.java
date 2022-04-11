package com.muesli.music.application.playlist;

import com.muesli.music.domain.playlist.PlaylistCommand;
import com.muesli.music.domain.playlist.PlaylistInfo;
import com.muesli.music.domain.playlist.PlaylistService;
import com.muesli.music.domain.user.token.UsertokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlaylistFacade {
    private final PlaylistService playlistService;
    private final UsertokenService usertokenService;

    /**
     * 플레이 리스트 정보 조회
     * @param playlistId
     * @param token
     * @param pageable
     * @return
     */
    public PlaylistInfo.Main findPlaylistInfo(Long playlistId, String token, Pageable pageable) {
        System.out.println("PlaylistFacade :: registerPlaylist");
        var usertokenInfo = usertokenService.findUsertokenInfo(token);
        return playlistService.findPlaylistInfo(playlistId, usertokenInfo.getUserInfo(), pageable);
    }

    /**
     * 내 플레이 리스트 목록 조회
     * @param token
     * @param pageable
     * @return
     */
    public List<PlaylistInfo.Main> retrieveMyPlaylist(String token, Pageable pageable) {
        System.out.println("PlaylistFacade :: retrieveMyPlaylist");
        usertokenService.checkUsertoken(token);
        var usertokenInfo = usertokenService.findUsertokenInfo(token);
        return playlistService.findPlaylistInfoMyList(usertokenInfo.getUserInfo(), pageable);
    }

    /**
     * 좋아하는 플레이 리스트 목록 조회
     * @param token
     * @param pageable
     * @return
     */
    public List<PlaylistInfo.Main> retrieveLikeList(String token, Pageable pageable) {
        System.out.println("PlaylistFacade :: retrieveLikeList");
        usertokenService.checkUsertoken(token);
        return playlistService.getLikeList(token, pageable);
    }

    /**
     * 플레이 리스트 생성
     *
     * @param command
     * @param token
     * @return
     */
    public PlaylistInfo.Main registerPlaylist(PlaylistCommand.RegisterPlaylistRequest command, String token) {
        System.out.println("PlaylistFacade :: registerPlaylist");
        usertokenService.checkUsertoken(token);
        var usertokenInfo = usertokenService.findUsertokenInfo(token);
        return playlistService.registerPlaylist(command, usertokenInfo.getUserInfo());
    }

    /**
     * 플레이 리스트 수정
     *
     * @param command
     * @param token
     * @return
     */
    public void updatePlaylist(PlaylistCommand.UpdatePlaylistRequest command, String token) {
        System.out.println("PlaylistFacade :: updatePlaylist");
        usertokenService.checkUsertoken(token);
        var usertokenInfo = usertokenService.findUsertokenInfo(token);
        playlistService.updatePlaylist(command, usertokenInfo.getUserInfo());

        var trackList = playlistService.getTrackToPlaylist(command.getId(), usertokenInfo.getUserInfo());
        var trackIdList = trackList.stream().map(playlistTrack -> playlistTrack.getTrack().getId()).collect(Collectors.toList());

        Collection<Long> oldTrackList = new ArrayList<>(trackIdList);
        Collection<Long> newTrackList = new ArrayList<>(command.getTrackList());
        List<Long> removeList = new ArrayList<>(oldTrackList);
        List<Long> addList = new ArrayList<>(newTrackList);

        removeList.removeAll(newTrackList);
        addList.removeAll(oldTrackList);


        var playlistTrackCommand = command.playlistTrackToCommand(command.getId(), command.getTrackList());
        playlistTrackCommand.setTrackList(removeList);
        playlistService.removeTrackToPlaylist(playlistTrackCommand, usertokenInfo.getUserInfo());

        playlistTrackCommand.setTrackList(addList);
        playlistService.addTrackToPlaylist(playlistTrackCommand, usertokenInfo.getUserInfo());
    }

    /**
     * 플레이 리스트 삭제
     * @param playlistId
     * @param token
     */
    public void removePlaylist(Long playlistId, String token) {
        System.out.println("PlaylistFacade :: removePlaylist");
        usertokenService.checkUsertoken(token);
        var usertokenInfo = usertokenService.findUsertokenInfo(token);

        playlistService.removePlaylist(playlistId, usertokenInfo.getUserInfo());
    }

    /**
     * 플레이 리스트에 트랙 추가
     *
     * @param command
     * @param token
     */
    public void addTrackToPlaylist(PlaylistCommand.TrackToPlaylistRequest command, String token) {
        System.out.println("PlaylistFacade :: addTrackToPlaylist");
        usertokenService.checkUsertoken(token);
        var usertokenInfo = usertokenService.findUsertokenInfo(token);
        playlistService.addTrackToPlaylist(command, usertokenInfo.getUserInfo());
    }

    /**
     * 플레이 리스트에 트랙 삭제
     *
     * @param command
     * @param token
     */
    public void removeTrackToPlaylist(PlaylistCommand.TrackToPlaylistRequest command, String token) {
        System.out.println("PlaylistFacade :: removeTrackToPlaylist");
        usertokenService.checkUsertoken(token);
        var usertokenInfo = usertokenService.findUsertokenInfo(token);
        playlistService.removeTrackToPlaylist(command, usertokenInfo.getUserInfo());
    }
}
