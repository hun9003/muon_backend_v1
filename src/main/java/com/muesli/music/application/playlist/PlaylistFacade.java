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
     * @param playlistId 플레이리스트 idx
     * @param pageable 플레이리스트 트랙 리스트 페이징을 위한 객체
     * @param token 유저 토큰
     * @return 플레이 리스트 정보
     */
    public PlaylistInfo.Main findPlaylistInfo(Long playlistId, Pageable pageable, String token) {
        System.out.println("PlaylistFacade :: findPlaylistInfo2");
        // 유저토큰을 통해 유저 토큰 정보 호출
        var usertoken = usertokenService.findUsertokenInfo(token);
        return playlistService.findPlaylistInfo(playlistId,  pageable, usertoken.getUserInfo());
    }

    /**
     * 내 플레이 리스트 목록 조회
     * @param token 유저 토큰
     * @param pageable 플레이리스트 트랙 리스트 페이징을 위한 객체
     * @return 플레이리스트 정보 리스트
     */
    public List<PlaylistInfo.PlayListInfo> retrieveMyPlaylist2(String token, Pageable pageable) {
        System.out.println("PlaylistFacade :: retrieveMyPlaylist");
        // 유저 토큰 유효성 검사
        usertokenService.checkUsertoken(token);

        // 유저 토큰을 통해 유저토큰 정보 호출
        var usertokenInfo = usertokenService.findUsertokenInfo(token);
        return playlistService.findPlaylistInfoMyList(usertokenInfo.getUserInfo(), pageable);
    }


    /**
     * 좋아하는 플레이 리스트 목록 조회
     * @param token 유저 토큰
     * @param pageable 플레이 리스트 페이징을 위한 객체
     * @return
     */
    public List<PlaylistInfo.PlayListInfo> retrieveLikeList2(String token, Pageable pageable) {
        System.out.println("PlaylistFacade :: retrieveLikeList");
        // 유저 토큰 유효성 검사
        usertokenService.checkUsertoken(token);

        // 유저 토큰을 통해 유저토큰 정보 호출
        var usertokenInfo = usertokenService.findUsertokenInfo(token);
        return playlistService.getLikeList(usertokenInfo.getUserInfo(), pageable);
    }

    /**
     * 플레이 리스트 생성
     * @param command 플레이 리스트 생성을 위한 데이터 객체
     * @param token 유저 토큰
     * @return
     */
    public PlaylistInfo.Main registerPlaylist(PlaylistCommand.RegisterPlaylistRequest command, String token) {
        System.out.println("PlaylistFacade :: registerPlaylist");
        // 유저 토큰 유효성 검사
        usertokenService.checkUsertoken(token);

        // 유저 토큰을 통해 유저토큰 정보 조회
        var usertokenInfo = usertokenService.findUsertokenInfo(token);
        return playlistService.registerPlaylist(command, usertokenInfo.getUserInfo());
    }

    /**
     * 플레이 리스트 수정
     * @param command 플레이 리스트 수정을 위한 데이터 객체
     * @param token 유저 토큰
     * @return
     */
    public void updatePlaylist(PlaylistCommand.UpdatePlaylistRequest command, String token) {
        System.out.println("PlaylistFacade :: updatePlaylist");
        // 유저 토큰 유효성 검사
        usertokenService.checkUsertoken(token);

        // 유저 토큰을 통해 유저토큰 정보 조회
        var usertokenInfo = usertokenService.findUsertokenInfo(token);

        // 플레이 리스트 수정
        playlistService.updatePlaylist(command, usertokenInfo.getUserInfo());

        // 플레이리스트에 담긴 트랙 리스트 조회
        var trackList = playlistService.getTrackToPlaylist(command.getId(), usertokenInfo.getUserInfo());

        // 수정 전 트랙 idx 리스트
        Collection<Long> oldTrackList = trackList.stream().map(playlistTrack -> playlistTrack.getTrack().getId()).collect(Collectors.toList());

        // 수정 후 트랙 idx 리스트
        Collection<Long> newTrackList = new ArrayList<>(command.getTrackList());

        // 삭제 될 트랙 리스트
        List<Long> removeList = new ArrayList<>(oldTrackList);
        removeList.removeAll(newTrackList);

        // 추가 될 트랙 리스트
        List<Long> addList = new ArrayList<>(newTrackList);
        addList.removeAll(oldTrackList);

        // 플레이 리스트의 트랙 최신화를 위한 데이터 객체 생성
        var playlistTrackCommand = command.playlistTrackToCommand(command.getId(), command.getTrackList());

        // 플레이 리스트 트랙 최신화 작업

        playlistTrackCommand.setTrackList(removeList);
        // 플레이 리스트 트랙 삭제
        playlistService.removeTrackToPlaylist(playlistTrackCommand, usertokenInfo.getUserInfo());

        playlistTrackCommand.setTrackList(addList);
        // 플레이 리스트 트랙 추가
        playlistService.addTrackToPlaylist(playlistTrackCommand, usertokenInfo.getUserInfo());
    }

    /**
     * 플레이 리스트 삭제
     * @param playlistId 플레이리스트 idx
     * @param token 유저 토큰
     */
    public void removePlaylist(Long playlistId, String token) {
        System.out.println("PlaylistFacade :: removePlaylist");
        // 유저 토큰 유효성 검사
        usertokenService.checkUsertoken(token);
        
        // 유저 토큰을 통해 유저토큰 정보 호출
        var usertokenInfo = usertokenService.findUsertokenInfo(token);

        playlistService.removePlaylist(playlistId, usertokenInfo.getUserInfo());
    }

    /**
     * 플레이 리스트에 트랙 추가
     * @param command 플레이 리스트에 트랙을 추가하기 위한 데이터 객체
     * @param token 유저 토큰
     */
    public void addTrackToPlaylist(PlaylistCommand.TrackToPlaylistRequest command, String token) {
        System.out.println("PlaylistFacade :: addTrackToPlaylist");
        // 유저 토큰 유효성 검사
        usertokenService.checkUsertoken(token);
        
        // 유저 토큰을 통해 유저토큰 정보 호출
        var usertokenInfo = usertokenService.findUsertokenInfo(token);
        playlistService.addTrackToPlaylist(command, usertokenInfo.getUserInfo());
    }

    /**
     * 플레이 리스트에 트랙 삭제
     * @param command
     * @param token
     */
    public void removeTrackToPlaylist(PlaylistCommand.TrackToPlaylistRequest command, String token) {
        System.out.println("PlaylistFacade :: removeTrackToPlaylist");
        // 유저 토큰 유효성 검사
        usertokenService.checkUsertoken(token);
        
        // 유저 토큰을 통해 유저토큰 정보 호출
        var usertokenInfo = usertokenService.findUsertokenInfo(token);
        playlistService.removeTrackToPlaylist(command, usertokenInfo.getUserInfo());
    }
}
