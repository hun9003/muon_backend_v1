package com.muesli.music.infrastructure.playlist;

import com.google.common.collect.Lists;
import com.muesli.music.common.exception.EntityNotFoundException;
import com.muesli.music.domain.artist.Artist;
import com.muesli.music.domain.artist.ArtistInfo;
import com.muesli.music.domain.like.Like;
import com.muesli.music.domain.like.LikeInfo;
import com.muesli.music.domain.playlist.Playlist;
import com.muesli.music.domain.playlist.PlaylistInfo;
import com.muesli.music.domain.playlist.PlaylistReader;
import com.muesli.music.domain.playlist.track.PlaylistTrack;
import com.muesli.music.domain.track.TrackInfo;
import com.muesli.music.domain.user.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class PlaylistReaderImpl implements PlaylistReader {
    private final PlaylistRepository playlistRepository;
    private final PlaylistTrackRepository playlistTrackRepository;

    @Override
    public Playlist getPlaylistBy(Long playlistId) {
        System.out.println("PlaylistReaderImpl :: getPlaylistBy");
        return playlistRepository.findPlaylistById(playlistId)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public PlaylistTrack getPlaylistTrack(Long playlistId, Long TrackId) {
        System.out.println("PlaylistReaderImpl :: getPlaylistTrack");
        return playlistTrackRepository.findPlaylistTrackByPlaylistAndTrack(playlistId, TrackId).orElseThrow(EntityNotFoundException::new);
    }

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

}
