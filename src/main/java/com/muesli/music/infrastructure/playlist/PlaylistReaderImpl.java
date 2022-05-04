package com.muesli.music.infrastructure.playlist;

import com.google.common.collect.Lists;
import com.muesli.music.common.exception.EntityNotFoundException;
import com.muesli.music.domain.playlist.Playlist;
import com.muesli.music.domain.playlist.PlaylistReader;
import com.muesli.music.domain.playlist.track.PlaylistTrack;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

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
     * 내 플레이리스트 목록 개수
     * @param userId 유저 idx
     * @return 플레이리스트 정보 리스트
     */
    @Override
    public int getPlaylistListCount(Long userId) {
        System.out.println("PlaylistReaderImpl :: getPlaylistListCount");
        return playlistRepository.countPlaylistByUserId(userId).orElse(0);
    }

    /**
     * 내 플레이리스트 목록 호출
     * @param userId 유저 idx
     * @param start
     * @param end
     * @return
     */
    @Override
    public List<Map<String, Object>> getPlaylistList(Long userId, int start, int end) {
        System.out.println("PlaylistReaderImpl :: getPlaylistList");
        return playlistRepository.findPlaylistByUserId(userId, start, end).orElse(Lists.newArrayList());
    }

    /**
     * 좋아하는 플레이리스트 목록 개수
     * @param userId 유저 idx
     * @return 플레이리스트 정보 리스트 호출
     */
    @Override
    public int getPlaylistLikeListCount(Long userId) {
        System.out.println("PlaylistReaderImpl :: getPlaylistLikeListCount");
        return playlistRepository.countLikeList(userId).orElse(0);
    }

    /**
     * 좋아하는 플레이리스트 목록 호출
     * @param userId 유저 idx
     * @param start
     * @param end
     * @return
     */
    @Override
    public List<Map<String, Object>> getPlaylistLikeList(Long userId, int start, int end) {
        System.out.println("PlaylistReaderImpl :: getPlaylistLikeList");
        return playlistRepository.findLikeList(userId, start, end).orElse(Lists.newArrayList());
    }
}
