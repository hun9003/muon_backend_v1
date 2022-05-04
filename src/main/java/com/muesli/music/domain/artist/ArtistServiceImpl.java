package com.muesli.music.domain.artist;

import com.muesli.music.common.util.ItemGenerator;
import com.muesli.music.domain.album.AlbumInfo;
import com.muesli.music.domain.album.AlbumReader;
import com.muesli.music.domain.artist.bios.Bios;
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
public class ArtistServiceImpl implements ArtistService{
    private final ArtistReader artistReader;
    private final AlbumReader albumReader;
    private final TrackReader trackReader;
    private final UsertokenReader usertokenReader;

    /**
     * 아티스트 정보 가져오기
     * @param artistId 아티스트 idx
     * @param pageable 페이징 처리를 위한 객체
     * @return 아티스트 정보 호출
     */
    @Override
    @Transactional(readOnly = true)
    public ArtistInfo.Main findArtistInfo(Long artistId, Pageable pageable) {
        System.out.println("ArtistServiceImpl :: findArtistInfo");
        var artist = artistReader.getArtistBy(artistId);
        var albumList = artistReader.getAlbumList(artist);
        
        // 아티스트 설명이 존재할 시 객체 생성
        var bios = artist.getBios().size() > 0 ? artist.getBios().iterator().next() : new Bios();
        var biosInfo = new ArtistInfo.BiosInfo(bios);

        artist.setViews(artist.getViews());

        var albumBasicList = albumList.stream().map(AlbumInfo.AlbumBasicInfo::new).collect(Collectors.toList());

        // 페이징
        var pageInfo = new PageInfo(pageable, albumBasicList.size());
        albumBasicList = albumBasicList.subList(pageInfo.getStartNum(), pageInfo.getEndNum());

        List<TrackInfo.TrackBasicInfo> trackInfoList = new ArrayList<>();
        for (var album : albumList) {
            var trackList = album.getTrackList();
            for (var track : trackList) {
                var trackInfo = new TrackInfo.TrackBasicInfo(track, new AlbumInfo.AlbumBasicInfo(album), new ArtistInfo.Main(artist));
                trackInfoList.add(trackInfo);
            }
        }

        // 페이징
        pageInfo = new PageInfo(pageable, trackInfoList.size());
        trackInfoList = trackInfoList.subList(pageInfo.getStartNum(), pageInfo.getEndNum());

        return new ArtistInfo.Main(artist, biosInfo, albumBasicList, trackInfoList);
    }

    @Override
    public ArtistInfo.Main2 findArtistInfo2(Long artistId, Pageable pageable) {
        System.out.println("ArtistServiceImpl :: findArtistInfo");
        var artist = artistReader.getArtistBy2(artistId);
        artist.setViews(artist.getViews());

        // 페이징
        var pageInfo = new PageInfo(pageable, albumReader.getAlbumListByArtistCount(artistId));

        // 아티스트 설명 호출
        var bios = artistReader.getBiosByArtist(artistId);
        var biosInfo = new ArtistInfo.BiosInfo(bios);

        // 앨범 리스트 호출
        var albumList = albumReader.getAlbumListByArtist(artistId, pageInfo.getStartNum(), pageInfo.getEndNum());
        var newAlbumList = new ArrayList<Map<String, Object>>();
        for (Map<String, Object> stringObjectMap : albumList) {
            var newAlbumMap = new HashMap<>(stringObjectMap);
            newAlbumList.add(newAlbumMap);
        }
        var albumListInfo = newAlbumList.stream().map(AlbumInfo.AlbumListInfo::new).collect(Collectors.toList());

        // 트랙 리스트 호출
        pageInfo = new PageInfo(pageable, trackReader.getTrackListByArtistCount(artistId));
        var trackList = trackReader.getTrackListByArtist(artistId, pageInfo.getStartNum(), pageInfo.getEndNum());
        var newTrackList = new ArrayList<Map<String, Object>>();
        for (Map<String, Object> stringObjectMap : trackList) {
            var newTrackMap = new HashMap<>(stringObjectMap);
            newTrackList.add(newTrackMap);
        }
        var trackListInfo = newTrackList.stream().map(TrackInfo.TrackListInfo::new).collect(Collectors.toList());

        return new ArtistInfo.Main2(artist, biosInfo, albumListInfo, trackListInfo);
    }

    /**
     * 아티스트 좋아요 리스트 조회
     * @param token 유저 토큰
     * @param pageable 페이징 처리를 위한 객체
     * @return 아티스트 정보 리스트
     */
    @Override
    @Transactional(readOnly = true)
    public List<ArtistInfo.Main> getLikeList(String token, Pageable pageable) {
        System.out.println("LikeServiceImpl :: getLikeArtistList");
        var usertoken = usertokenReader.getUsertoken(token);
        var artistInfoList = artistReader.getArtistLikeList(usertoken.getUser().getId());
        // 페이징
        var pageInfo = new PageInfo(pageable, artistInfoList.size());
        return artistInfoList.subList(pageInfo.getStartNum(), pageInfo.getEndNum());
    }
    @Override
    @Transactional(readOnly = true)
    public List<ArtistInfo.ArtistListInfo> getLikeList2(String token, Pageable pageable) {
        System.out.println("LikeServiceImpl :: getLikeArtistList");
        var usertoken = usertokenReader.getUsertoken(token);
        var pageInfo = new PageInfo(pageable, artistReader.getArtistLikeListCount(usertoken.getUser().getId()));
        var artistList = artistReader.getArtistLikeList(usertoken.getUser().getId(), pageInfo.getStartNum(), pageInfo.getEndNum());
        var newArtistList = ItemGenerator.makeItemListMap(artistList);
        return newArtistList.stream().map(ArtistInfo.ArtistListInfo::new).collect(Collectors.toList());
    }

    /**
     * 아티스트 검색 결과 조회
     * @param command 검색 정보가 담긴 데이터 객체
     * @param pageable 페이징 처리를 위한 객체
     * @return 아티스트 검색 결과 리스트
     */
    @Override
    public List<ArtistInfo.SearchInfo> getSearchArtist(SearchCommand.SearchRequest command, Pageable pageable) {
        System.out.println("ArtistServiceImpl :: getSearchArtist");
        // 페이징
        var pageInfo = new PageInfo(pageable, command.getArtistCount());
        var artistList = artistReader.getSearchArtist(command.getKeyword(), command.getType(), pageInfo.getStartNum(), pageInfo.getEndNum());
        var newArtistList = new ArrayList<Map<String, Object>>();
        for (Map<String, Object> stringObjectMap : artistList) {
            var newArtistMap = new HashMap<>(stringObjectMap);
            newArtistList.add(newArtistMap);
        }
        return newArtistList.stream().map(ArtistInfo.SearchInfo::new).collect(Collectors.toList());
    }

    /**
     * 아티스트 검색 결과 개수
     * @param command 검색 정보가 담긴 데이터 객체
     * @return 아티스트 검색 결과 개수
     */
    @Override
    public int getSearchArtistCount(SearchCommand.SearchRequest command) {
        System.out.println("ArtistServiceImpl :: getSearchArtistCount");
        return artistReader.getSearchArtistCount(command.getKeyword());
    }
}
