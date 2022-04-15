package com.muesli.music.domain.artist;

import com.muesli.music.domain.album.AlbumInfo;
import com.muesli.music.domain.artist.bios.Bios;
import com.muesli.music.domain.search.SearchCommand;
import com.muesli.music.domain.track.TrackInfo;
import com.muesli.music.domain.user.UserInfo;
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
    private final UsertokenReader usertokenReader;

    /**
     * 아티스트 정보 가져오기
     * @param artistId
     * @param userInfo
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public ArtistInfo.Main findArtistInfo(Long artistId, UserInfo.Main userInfo, Pageable pageable) {
        System.out.println("ArtistServiceImpl :: findArtistInfo");
        var artist = artistReader.getArtistBy(artistId);
        var albumList = artistReader.getAlbumList(artist);
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

    /**
     * 좋아요 리스트 조회
     * @param token
     * @return
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

    /**
     * 아티스트 검색 결과 조회
     * @param command
     * @param pageable
     * @return
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
     * @param command
     * @return
     */
    @Override
    public int getSearchArtistCount(SearchCommand.SearchRequest command) {
        System.out.println("ArtistServiceImpl :: getSearchArtistCount");
        return artistReader.getSearchArtistCount(command.getKeyword());
    }
}
