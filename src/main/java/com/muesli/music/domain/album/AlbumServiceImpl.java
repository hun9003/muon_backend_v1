package com.muesli.music.domain.album;

import com.muesli.music.domain.artist.ArtistInfo;
import com.muesli.music.domain.genre.GenreInfo;
import com.muesli.music.domain.genre.GenreReader;
import com.muesli.music.domain.search.SearchCommand;
import com.muesli.music.domain.track.TrackInfo;
import com.muesli.music.domain.track.TrackReader;
import com.muesli.music.domain.user.token.UsertokenReader;
import com.muesli.music.interfaces.user.PageInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlbumServiceImpl implements AlbumService {
    private final AlbumReader albumReader;
    private final TrackReader trackReader;
    private final UsertokenReader usertokenReader;
    private final GenreReader genreReader;

    /**
     * 앨범 정보 가져오기
     * @param albumId 앨범 idx
     * @return 앨범 정보
     */
    @Override
    @Transactional(readOnly = true)
    public AlbumInfo.Main findAlbumInfo(Long albumId, Pageable pageable) {
        System.out.println("AlbumServiceImpl :: findAlbumInfo");

        // 앨범 데이터 호출
        var album = albumReader.getAlbumBy2(albumId);
        album.setViews(album.getViews());

        var trackCount = trackReader.getTrackByAlbumCount(albumId);
        var pageInfo = new PageInfo(pageable, trackCount);

        var trackList = trackReader.getTrackByAlbum(albumId, pageInfo.getStartNum(), pageInfo.getEndNum());
        var newTrackList = new ArrayList<Map<String, Object>>();
        for (Map<String, Object> stringObjectMap : trackList) {
            var newAlbumMap = new HashMap<>(stringObjectMap);
            newTrackList.add(newAlbumMap);
        }
        var trackListInfo = newTrackList.stream().map(TrackInfo.TrackListInfo::new).collect(Collectors.toList());

        var albumInfo = new AlbumInfo.Main(album, trackListInfo);
        albumInfo.setArtistInfo(new ArtistInfo.Main(album.getArtist()));

        return albumInfo;
    }


    /**
     * 좋아요 리스트 조회
     * @param token 유저 토큰
     * @param pageable 페이징 처리를 위한 객체
     * @return 앨범 정보 리스트
     */
    @Override
    @Transactional(readOnly = true)
    public List<AlbumInfo.Main> getLikeList(String token, Pageable pageable) {
        System.out.println("AlbumServiceImpl :: getLikeList");
        var usertoken = usertokenReader.getUsertoken(token);
        var userId = usertoken.getUser().getId();
        var albumInfoList = albumReader.getAlbumLikeList(userId).stream().peek(
                main -> {
                    var track = trackReader.getTrackArtist(main.getId());
                    if (track != null) {
                        main.setArtistInfo(new ArtistInfo.Main(track.getTrackArtists().iterator().next().getArtist()));
                    }
                }
        ).collect(Collectors.toList());
        // 페이징
        var pageInfo = new PageInfo(pageable, albumInfoList.size());
        return albumInfoList.subList(pageInfo.getStartNum(), pageInfo.getEndNum());
    }

    /**
     * 최신 앨범 조회
     * @param pageable 페이징 처리를 위한 객체 
     * @return 최신 앨범 정보 리스트
     */
    @Override
    public List<AlbumInfo.NewestAlbumInfo> getNewAlbum(Pageable pageable) {
        System.out.println("AlbumServiceImpl :: getNewAlbum");
        // 페이징
        var pageInfo = new PageInfo(pageable, 500);
        var albumList = albumReader.getNewAlbum(pageInfo.getStartNum(), pageInfo.getEndNum());
        var newAlbumList = new ArrayList<Map<String, Object>>();
        for (Map<String, Object> stringObjectMap : albumList) {
            var newAlbumMap = new HashMap<>(stringObjectMap);
            newAlbumList.add(newAlbumMap);
        }
        return newAlbumList.stream().map(AlbumInfo.NewestAlbumInfo::new).collect(Collectors.toList());
    }

    /**
     * 앨범 키워드로 검색 조회
     * @param command 검색 정보가 담긴 데이터 객체
     * @param pageable 페이징 처리를 위한 객체
     * @return 앨범 검색 결과 리스트
     */
    @Override
    public List<AlbumInfo.SearchInfo> getSearchAlbum(SearchCommand.SearchRequest command, Pageable pageable) {
        System.out.println("AlbumServiceImpl :: getSearchAlbum");
        // 페이징
        var pageInfo = new PageInfo(pageable, command.getAlbumCount());
        var albumList = albumReader.getSearchAlbum(command.getKeyword(), command.getType(), pageInfo.getStartNum(), pageInfo.getEndNum());
        var newAlbumList = new ArrayList<Map<String, Object>>();
        for (Map<String, Object> stringObjectMap : albumList) {
            var newAlbumMap = new HashMap<>(stringObjectMap);
            newAlbumList.add(newAlbumMap);
        }
        return newAlbumList.stream().map(AlbumInfo.SearchInfo::new).collect(Collectors.toList());
    }

    /**
     * 앨범 검색 결과 개수
     * @param command 검색 정보가 담긴 데이터 객체
     * @return 앨범 검색 결과 개수
     */
    @Override
    public int getSearchAlbumCount(SearchCommand.SearchRequest command) {
        System.out.println("AlbumServiceImpl :: getSearchAlbumCount");
        return albumReader.getSearchAlbumCount(command.getKeyword());
    }

    /**
     * 장르별 앨범 리스트 호출
     * @param genreId 장르 idx
     * @param pageable 페이징 처리를 위한 객체
     * @return 장르별 앨범 정보 리스트
     */
    @Override
    public List<AlbumInfo.GenreAlbumInfo> getGenreAlbumList(Long genreId, Pageable pageable) {
        // 페이징
        var albumList = albumReader.getGenreAlbumList(genreId, pageable);
        var newAlbumList = new ArrayList<Map<String, Object>>();
        for (Map<String, Object> stringObjectMap : albumList) {
            var newAlbumMap = new HashMap<>(stringObjectMap);
            newAlbumList.add(newAlbumMap);
        }
        return newAlbumList.stream().map(AlbumInfo.GenreAlbumInfo::new).collect(Collectors.toList());
    }

    /**
     * 장르별 앨범 리스트 호출 (전체)
     * @param pageable 페이징 처리를 위한 객체
     * @return 장르별 앨범 정보 리스트 (전체)
     */
    @Override
    public List<GenreInfo.Main> getGenreAlbumListAll(Pageable pageable) {
        var genreList = genreReader.getGenreParentList();
        return genreList.stream().map(
                genre -> {
                    var genreInfo = new GenreInfo.Main(genre);
                    var albumList = albumReader.getGenreAlbumList(genre.getId(), pageable);
                    var newAlbumList = new ArrayList<Map<String, Object>>();
                    for (Map<String, Object> stringObjectMap : albumList) {
                        var newAlbumMap = new HashMap<>(stringObjectMap);
                        newAlbumList.add(newAlbumMap);
                    }
                    genreInfo.setAlbumList(newAlbumList.stream().map(AlbumInfo.GenreAlbumInfo::new).collect(Collectors.toList()));
                    return genreInfo;
                }
        ).collect(Collectors.toList());
    }
}
