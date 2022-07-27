package com.muesli.music.domain.artist;

import com.muesli.music.common.util.ItemGenerator;
import com.muesli.music.domain.album.AlbumInfo;
import com.muesli.music.domain.album.AlbumReader;
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
    public ArtistInfo.Main findArtistInfo(Long artistId, Pageable pageable) {
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
        var newAlbumList = ItemGenerator.makeItemListMap(albumList);
        var albumListInfo = newAlbumList.stream().map(stringObjectMap -> {
            var albumId = Long.parseLong(String.valueOf(stringObjectMap.get("id")));;
            var trackCount = trackReader.getTrackByAlbumCount(albumId);
            var trackList = trackReader.getTrackListByAlbum(albumId, 0, trackCount);
            var newTrackList = ItemGenerator.makeItemListMap(trackList);
            var trackListInfo = newTrackList.stream().map(TrackInfo.TrackListInfo::new).collect(Collectors.toList());
            return new AlbumInfo.AlbumListInfo(stringObjectMap, trackListInfo);
        }).collect(Collectors.toList());

        // 트랙 리스트 호출
        pageInfo = new PageInfo(pageable, trackReader.getTrackListByArtistCount(artistId));
        var trackList = trackReader.getTrackListByArtist(artistId, pageInfo.getStartNum(), pageInfo.getEndNum());
        var newTrackList = ItemGenerator.makeItemListMap(trackList);
        var trackListInfo = newTrackList.stream().map(TrackInfo.TrackListInfo::new).collect(Collectors.toList());

        return new ArtistInfo.Main(artist, biosInfo, albumListInfo, trackListInfo);
    }

    /**
     * 아티스트 좋아요 리스트 조회
     * @param token 유저 토큰
     * @param pageable 페이징 처리를 위한 객체
     * @return 아티스트 정보 리스트
     */
    @Override
    @Transactional(readOnly = true)
    public List<ArtistInfo.ArtistListInfo> getLikeList(String token, Pageable pageable) {
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
    public List<ArtistInfo.ArtistListInfo> getSearchArtist(SearchCommand.SearchRequest command, Pageable pageable) {
        System.out.println("ArtistServiceImpl :: getSearchArtist");
        // 페이징
        var pageInfo = new PageInfo(pageable, command.getArtistCount());
        var artistList = artistReader.getSearchArtist(command.getKeyword(), command.getType(), pageInfo.getStartNum(), pageInfo.getEndNum());
        var newArtistList = ItemGenerator.makeItemListMap(artistList);
        return newArtistList.stream().map(ArtistInfo.ArtistListInfo::new).collect(Collectors.toList());
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
