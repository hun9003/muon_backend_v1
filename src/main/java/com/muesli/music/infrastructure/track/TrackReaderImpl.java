package com.muesli.music.infrastructure.track;

import com.google.common.collect.Lists;
import com.muesli.music.common.exception.InvalidParamException;
import com.muesli.music.common.util.Constant;
import com.muesli.music.domain.track.Track;
import com.muesli.music.domain.track.TrackReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class TrackReaderImpl implements TrackReader {
    private final TrackRepository trackRepository;

    @Override
    public Track getTrackBy(Long trackId) {
        System.out.println("TrackReaderImpl :: getTrackBy");
        return trackRepository.findTrackById(trackId)
                .orElseThrow(InvalidParamException::new);
    }

    @Override
    public int getTrackByAlbumCount(Long albumId) {
        System.out.println("TrackReaderImpl :: getTrackByAlbumCount");
        return trackRepository.countTrackByAlbumId(albumId).orElse(0);
    }

    @Override
    public List<Map<String, Object>> getTrackListByAlbum(Long albumId, int start, int end) {
        System.out.println("TrackReaderImpl :: getTrackArtist");
        return trackRepository.findTrackByAlbumId(albumId, start, end)
                .orElse(null);
    }

    @Override
    public int getTrackListByArtistCount(Long artistId) {
        System.out.println("TrackReaderImpl :: getTrackListByArtistCount");
        return trackRepository.countTrackByArtistId(artistId).orElse(0);
    }

    @Override
    public List<Map<String, Object>> getTrackListByArtist(Long artistId, int startNum, int endNum) {
        System.out.println("TrackReaderImpl :: getTrackListByArtist");
        return trackRepository.findTrackByArtistId(artistId, startNum, endNum).orElse(Lists.newArrayList());
    }

    @Override
    public int getTrackLikeListCount(Long userId) {
        System.out.println("TrackReaderImpl :: getTrackLikeListCount");
        return trackRepository.countLikeList(userId).orElse(0);
    }

    @Override
    public List<Map<String, Object>> getTrackLikeList(Long userId, int start, int end) {
        System.out.println("TrackReaderImpl :: getTrackLikeList");
        return trackRepository.findLikeList(userId, start, end).orElse(Lists.newArrayList());
    }

    @Override
    public List<Map<String, Object>> getTrackRank(String begin, String end, Pageable pageable) {
        System.out.println("TrackReaderImpl :: getTrackRank");
        return trackRepository.findTrackRank(begin, end, pageable.getPageSize()).orElseThrow(InvalidParamException::new);
    }

    @Override
    public List<Map<String, Object>> getTrackGenreRank(String begin, String end, Pageable pageable, Long genreId) {
        System.out.println("TrackReaderImpl :: getTrackGenreRank");
        return trackRepository.findTrackGenreRank(begin, end, pageable.getPageSize(), genreId).orElseThrow(InvalidParamException::new);
    }

    @Override
    public List<Map<String, Object>> getNewTrack(int start, int end) {
        System.out.println("TrackReaderImpl :: getNewTrack");
        return trackRepository.findNewTrack(start, end).orElseThrow(InvalidParamException::new);
    }

    @Override
    public List<Map<String, Object>> getUserHistoryTrack(Long userId, int start, int end) {
        System.out.println("TrackReaderImpl :: getUserHistoryTrack");
        return trackRepository.findUserHistoryTrack(userId, start, end).orElseThrow(InvalidParamException::new);
    }

    @Override
    public int getSearchTrackCount(String keyword) {
        System.out.println("TrackReaderImpl :: getSearchTrackCount");
        return trackRepository.findSearchTrackCount(keyword).orElse(0);
    }

    @Override
    public List<Map<String, Object>> getSearchTrack(String keyword, String type, int start, int end) {
        System.out.println("TrackReaderImpl :: getNewTrack");
        List<Map<String, Object>> trackList;

        switch (type) {
            case Constant.Order.SIMILAR : trackList = trackRepository.findSearchTrackOrderBySimilar(keyword, start, end).orElse(new ArrayList<>()); break;
            case Constant.Order.NEWEST : trackList = trackRepository.findSearchTrackOrderByNewest(keyword, start, end).orElse(new ArrayList<>()); break;
            case Constant.Order.POPULARITY :
            default: trackList = trackRepository.findSearchTrackOrderByPopularity(keyword, start, end).orElse(new ArrayList<>());
        }
        return trackList;
    }

    @Override
    public int getGenreTrackCount(Long genreId) {
        System.out.println("TrackReaderImpl :: getGenreTrackCount");
        return trackRepository.findGenreTrackCount(genreId).orElse(0);
    }

    @Override
    public List<Map<String, Object>> getGenreTrackList(Long genreId, String type, int start, int end) {
        System.out.println("TrackReaderImpl :: getGenreTrackList");
        List<Map<String, Object>> trackList;
        switch (type) {
            case Constant.Order.NEWEST : trackList = trackRepository.findGenreTrackOrderByNewest(genreId, start, end).orElse(new ArrayList<>()); break;
            case Constant.Order.POPULARITY:
            default: trackList = trackRepository.findGenreTrackOrderByPopularity(genreId, start, end).orElse(new ArrayList<>());
        }
        return trackList;
    }

    @Override
    public int getPlaylistTrackCount(Long playlistId) {
        System.out.println("TrackReaderImpl :: getPlaylistTrackCount");
        return trackRepository.countTrackByPlaylist(playlistId).orElse(0);
    }

    @Override
    public List<Map<String, Object>> getPlaylistTrackList(Long playlistId, int start, int end) {
        System.out.println("TrackReaderImpl :: getPlaylistTrackList");
        return trackRepository.findTrackByPlaylist(playlistId, start, end).orElse(Lists.newArrayList());
    }
}

