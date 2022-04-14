package com.muesli.music.application.search;

import com.muesli.music.domain.album.AlbumInfo;
import com.muesli.music.domain.album.AlbumService;
import com.muesli.music.domain.artist.ArtistInfo;
import com.muesli.music.domain.artist.ArtistService;
import com.muesli.music.domain.search.SearchCommand;
import com.muesli.music.domain.track.TrackInfo;
import com.muesli.music.domain.track.TrackService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchFacade {
    private final TrackService trackService;
    private final AlbumService albumService;
    private final ArtistService artistService;
    /*
    검색 요구사항

    - 키워드에 대한 결과 리스트 전송

    - 검색 결과 리스트 전송

    - 곡 결과 리스트 전송 ( 인기순, 최신순 )

    - 앨범 결과 리스트 전송 ( 인기순, 최신순 )

    - 아티스트 결과 리스트 전송 ( 가나다순, 인기순 )

    - 가사 결과 리스트 전송 ( 가나다순, 최신순 )
     */


    /**
     * 트랙 키워드 검색 조회
     * @param command
     * @param pageable
     * @return
     */
    public List<TrackInfo.SearchInfo> retrieveSearchTrack(SearchCommand.SearchRequest command, Pageable pageable) {
        System.out.println("TrackFacade :: retrieveNewTrack");
        return trackService.getSearchTrack(command, pageable);
    }

    /**
     * 앨범 키워드 검색 조회
     * @param command
     * @param pageable
     * @return
     */
    public List<AlbumInfo.SearchInfo> retrieveSearchAlbum(SearchCommand.SearchRequest command, Pageable pageable) {
        System.out.println("AlbumFacade :: retrieveNewAlbum");
        return albumService.getSearchAlbum(command, pageable);
    }

    /**
     * 아티스트 키워드 검색 조회
     * @param command
     * @param pageable
     * @return
     */
    public List<ArtistInfo.SearchInfo> retrieveSearchArtist(SearchCommand.SearchRequest command, Pageable pageable) {
        System.out.println("ArtistFacade :: retrieveNewArtist");
        return artistService.getSearchArtist(command, pageable);
    }
}
