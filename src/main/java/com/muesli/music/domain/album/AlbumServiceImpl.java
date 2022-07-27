package com.muesli.music.domain.album;

import com.muesli.music.common.util.ItemGenerator;
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

import java.util.List;
import java.util.stream.Collectors;
// CRUD
// CUD -> store
// R -> Reader
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
        var album = albumReader.getAlbumBy(albumId);
        album.setViews(album.getViews());

        var trackCount = trackReader.getTrackByAlbumCount(albumId);
        var pageInfo = new PageInfo(pageable, trackCount);

        var trackList = trackReader.getTrackListByAlbum(albumId, pageInfo.getStartNum(), pageInfo.getEndNum());

        var newTrackList = ItemGenerator.makeItemListMap(trackList);
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
    public List<AlbumInfo.AlbumListInfo> getLikeList2(String token, Pageable pageable) {
        System.out.println("AlbumServiceImpl :: getLikeList");
        var usertoken = usertokenReader.getUsertoken(token);
        var userId = usertoken.getUser().getId();
        // 페이징
        var pageInfo = new PageInfo(pageable, albumReader.getAlbumLikeListCount(userId));
        var albumList = albumReader.getAlbumLikeList(userId, pageInfo.getStartNum(), pageInfo.getEndNum());
        var newAlbumList = ItemGenerator.makeItemListMap(albumList);

        return newAlbumList.stream().map(stringObjectMap -> {
            var albumId = Long.parseLong(String.valueOf(stringObjectMap.get("id")));;
            var trackCount = trackReader.getTrackByAlbumCount(albumId);

            var trackList = trackReader.getTrackListByAlbum(albumId, 0, trackCount);
            var newTrackList = ItemGenerator.makeItemListMap(trackList);
            var trackListInfo = newTrackList.stream().map(TrackInfo.TrackListInfo::new).collect(Collectors.toList());
            return new AlbumInfo.AlbumListInfo(stringObjectMap, trackListInfo);
        }).collect(Collectors.toList());
    }

    /**
     * 최신 앨범 조회
     * @param pageable 페이징 처리를 위한 객체 
     * @return 최신 앨범 정보 리스트
     */
    @Override
    public List<AlbumInfo.AlbumListInfo> getNewAlbum(Pageable pageable) {
        System.out.println("AlbumServiceImpl :: getNewAlbum");
        // 페이징
        var pageInfo = new PageInfo(pageable, 500);
        var albumList = albumReader.getNewAlbum(pageInfo.getStartNum(), pageInfo.getEndNum());
        var newAlbumList = ItemGenerator.makeItemListMap(albumList);
        return newAlbumList.stream().map(stringObjectMap -> {
            var albumId = Long.parseLong(String.valueOf(stringObjectMap.get("id")));;
            var trackCount = trackReader.getTrackByAlbumCount(albumId);

            var trackList = trackReader.getTrackListByAlbum(albumId, 0, trackCount);
            var newTrackList = ItemGenerator.makeItemListMap(trackList);
            var trackListInfo = newTrackList.stream().map(TrackInfo.TrackListInfo::new).collect(Collectors.toList());
            return new AlbumInfo.AlbumListInfo(stringObjectMap, trackListInfo);
        }).collect(Collectors.toList());
    }

    /**
     * 앨범 키워드로 검색 조회
     * @param command 검색 정보가 담긴 데이터 객체
     * @param pageable 페이징 처리를 위한 객체
     * @return 앨범 검색 결과 리스트
     */
    @Override
    public List<AlbumInfo.AlbumListInfo> getSearchAlbum(SearchCommand.SearchRequest command, Pageable pageable) {
        System.out.println("AlbumServiceImpl :: getSearchAlbum");
        // 페이징
        var pageInfo = new PageInfo(pageable, command.getAlbumCount());
        var albumList = albumReader.getSearchAlbum(command.getKeyword(), command.getType(), pageInfo.getStartNum(), pageInfo.getEndNum());
        var newAlbumList = ItemGenerator.makeItemListMap(albumList);

        return newAlbumList.stream().map(stringObjectMap -> {
            var albumId = Long.parseLong(String.valueOf(stringObjectMap.get("id")));;
            var trackCount = trackReader.getTrackByAlbumCount(albumId);

            var trackList = trackReader.getTrackListByAlbum(albumId, 0, trackCount);
            var newTrackList = ItemGenerator.makeItemListMap(trackList);
            var trackListInfo = newTrackList.stream().map(TrackInfo.TrackListInfo::new).collect(Collectors.toList());
            return new AlbumInfo.AlbumListInfo(stringObjectMap, trackListInfo);
        }).collect(Collectors.toList());
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
    public List<AlbumInfo.AlbumListInfo> getGenreAlbumList(Long genreId, Pageable pageable) {
        System.out.println("AlbumServiceImpl :: getGenreAlbumList");
        var albumList = albumReader.getGenreAlbumList(genreId, pageable);
        var newAlbumList = ItemGenerator.makeItemListMap(albumList);

        return newAlbumList.stream().map(stringObjectMap -> {
            var albumId = Long.parseLong(String.valueOf(stringObjectMap.get("id")));;
            var trackCount = trackReader.getTrackByAlbumCount(albumId);

            var trackList = trackReader.getTrackListByAlbum(albumId, 0, trackCount);
            var newTrackList = ItemGenerator.makeItemListMap(trackList);
            var trackListInfo = newTrackList.stream().map(TrackInfo.TrackListInfo::new).collect(Collectors.toList());
            return new AlbumInfo.AlbumListInfo(stringObjectMap, trackListInfo);
        }).collect(Collectors.toList());
    }

    /**
     * 장르별 앨범 리스트 호출 (전체)
     * @param pageable 페이징 처리를 위한 객체
     * @return 장르별 앨범 정보 리스트 (전체)
     */
    @Override
    public List<GenreInfo.Main> getGenreAlbumListAll(Pageable pageable) {
        System.out.println("AlbumServiceImpl :: getGenreAlbumListAll");
        var genreList = genreReader.getGenreParentList();
        return genreList.stream().map(
                genre -> {
                    var genreInfo = new GenreInfo.Main(genre);
                    var albumList = albumReader.getGenreAlbumList(genre.getId(), pageable);
                    var newAlbumList = ItemGenerator.makeItemListMap(albumList);
                    var albumInfoList = newAlbumList.stream().map(stringObjectMap -> {
                        var albumId = Long.parseLong(String.valueOf(stringObjectMap.get("id")));;
                        var trackCount = trackReader.getTrackByAlbumCount(albumId);

                        var trackList = trackReader.getTrackListByAlbum(albumId, 0, trackCount);
                        var newTrackList = ItemGenerator.makeItemListMap(trackList);
                        var trackListInfo = newTrackList.stream().map(TrackInfo.TrackListInfo::new).collect(Collectors.toList());
                        return new AlbumInfo.AlbumListInfo(stringObjectMap, trackListInfo);
                    }).collect(Collectors.toList());
                    genreInfo.setAlbumList(albumInfoList);
                    return genreInfo;
                }
        ).collect(Collectors.toList());
    }

    /**
     * 채널별 앨범 리스트 호출
     * @param channelId 채널 idx
     * @param pageable 페이징 처리를 위한 객체
     * @return 채널별 앨범 리스트
     */
    @Override
    public List<AlbumInfo.AlbumListInfo> getChannelAlbum(Long channelId, Pageable pageable) {
        System.out.println("AlbumServiceImpl :: getChannelAlbum");
        // 페이징
        var pageInfo = new PageInfo(pageable, albumReader.getChannelAlbumCount(channelId));
        var albumList = albumReader.getChannelAlbumList(channelId, pageInfo.getStartNum(), pageInfo.getEndNum());
        var newAlbumList = ItemGenerator.makeItemListMap(albumList);
        return newAlbumList.stream().map(stringObjectMap -> {
            var albumId = Long.parseLong(String.valueOf(stringObjectMap.get("id")));;
            var trackCount = trackReader.getTrackByAlbumCount(albumId);

            var trackList = trackReader.getTrackListByAlbum(albumId, 0, trackCount);
            var newTrackList = ItemGenerator.makeItemListMap(trackList);
            var trackListInfo = newTrackList.stream().map(TrackInfo.TrackListInfo::new).collect(Collectors.toList());
            return new AlbumInfo.AlbumListInfo(stringObjectMap, trackListInfo);
        }).collect(Collectors.toList());
    }
}
