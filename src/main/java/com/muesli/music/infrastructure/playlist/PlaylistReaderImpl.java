package com.muesli.music.infrastructure.playlist;

import com.google.common.collect.Lists;
import com.muesli.music.common.exception.EntityNotFoundException;
import com.muesli.music.domain.artist.Artist;
import com.muesli.music.domain.artist.ArtistInfo;
import com.muesli.music.domain.playlist.Playlist;
import com.muesli.music.domain.playlist.PlaylistInfo;
import com.muesli.music.domain.playlist.PlaylistReader;
import com.muesli.music.domain.playlist.track.PlaylistTrack;
import com.muesli.music.domain.track.TrackInfo;
import com.muesli.music.domain.user.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class PlaylistReaderImpl implements PlaylistReader {
    private final PlaylistRepository playlistRepository;
    private final PlaylistTrackRepository playlistTrackRepository;

    /**
     * 플레이리스트 데이터 호출
     * @param playlistId 플레이리스트 idx
     * @return 플레이리스트 데이터
     */
    @Override
    public Playlist getPlaylistBy(Long playlistId) {
        System.out.println("PlaylistReaderImpl :: getPlaylistBy");
        return playlistRepository.findPlaylistById(playlistId)
                .orElseThrow(EntityNotFoundException::new);
    }
    @Override
    public Playlist getPlaylistBy2(Long playlistId) {
        System.out.println("PlaylistReaderImpl :: getPlaylistBy");
        return playlistRepository.findById(playlistId)
                .orElseThrow(EntityNotFoundException::new);
    }

    /**
     * 플레이리스트 트랙 데이터 호출
     * @param playlistId 플레이리스트 idx
     * @param trackId 트랙 idx
     * @return 플레이리스트 트랙 데이터
     */
    @Override
    public PlaylistTrack getPlaylistTrack(Long playlistId, Long trackId) {
        System.out.println("PlaylistReaderImpl :: getPlaylistTrack");
        return playlistTrackRepository.findPlaylistTrackByPlaylistAndTrack(playlistId, trackId).orElseThrow(EntityNotFoundException::new);
    }

    /**
     * 내 플레이리스트 목록 호출
     * @param userInfo 유저 정보
     * @return 플레이리스트 정보 리스트
     */
    @Override
    public List<PlaylistInfo.Main> getPlaylistList(UserInfo.Main userInfo) {
        System.out.println("PlaylistReaderImpl :: getPlaylistList");
        var playlistList = playlistRepository.findPlaylistByUserId(userInfo.getId()).orElse(Lists.newArrayList());
        return playlistList.stream().map(
                playlist -> {
                    var trackInfoList = playlist.getPlaylistTrackList().stream().map(
                            playlistTrack -> {
                                var track = playlistTrack.getTrack();
                                return new TrackInfo.Main(track, new ArtistInfo.Main(new Artist()));
                            }
                    ).collect(Collectors.toList());
                    return new PlaylistInfo.Main(playlist, userInfo, trackInfoList);
                }
        ).collect(Collectors.toList());
    }

    @Override
    public int getPlaylistListCount(Long userId) {
        System.out.println("PlaylistReaderImpl :: getPlaylistListCount");
        return playlistRepository.countPlaylistByUserId(userId).orElse(0);
    }

    @Override
    public List<Map<String, Object>> getPlaylistList(Long userId, int start, int end) {
        System.out.println("PlaylistReaderImpl :: getPlaylistList");
        return playlistRepository.findPlaylistByUserId(userId, start, end).orElse(Lists.newArrayList());
    }

    /**
     * 좋아하는 플레이리스트 목록 호출
     * @param userInfo 유저 정보
     * @return 플레이리스트 정보 리스트 호출
     */
    @Override
    public List<PlaylistInfo.Main> getPlaylistLikeList(UserInfo.Main userInfo) {
        System.out.println("PlaylistReaderImpl :: getPlaylistLikeList");
        var playlistList = playlistRepository.findAllLikeList(userInfo.getId())
                .orElseThrow(EntityNotFoundException::new);

        return playlistList.stream().map(
                playlist -> {
                    var trackInfoList = playlist.getPlaylistTrackList().stream().map(
                            playlistTrack -> {
                                var track = playlistTrack.getTrack();
                                return new TrackInfo.Main(track, new ArtistInfo.Main(new Artist()));
                            }
                    ).collect(Collectors.toList());
                    return new PlaylistInfo.Main(playlist, userInfo, trackInfoList);
                }
        ).collect(Collectors.toList());
    }

    @Override
    public int getPlaylistLikeListCount(Long userId) {
        System.out.println("PlaylistReaderImpl :: getPlaylistLikeListCount");
        return playlistRepository.countLikeList(userId).orElse(0);
    }

    @Override
    public List<Map<String, Object>> getPlaylistLikeList(Long userId, int start, int end) {
        System.out.println("PlaylistReaderImpl :: getPlaylistLikeList");
        return playlistRepository.findLikeList(userId, start, end).orElse(Lists.newArrayList());
    }
}
