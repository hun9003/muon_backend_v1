package com.muesli.music.application.genre;

import com.muesli.music.domain.album.AlbumInfo;
import com.muesli.music.domain.album.AlbumService;
import com.muesli.music.domain.genre.GenreCommand;
import com.muesli.music.domain.genre.GenreInfo;
import com.muesli.music.domain.genre.GenreService;
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
public class GenreFacade {
    private final GenreService genreService;
    private final AlbumService albumService;
    private final TrackService trackService;

    /**
     * 키워드를 통해 장르 리스트 호출
     * @param command 장르 데이터 서치를 위한 객체
     * @return
     */
    @Deprecated 
    public List<Object> getGenreItemList(GenreCommand.GenreRequest command) {
        System.out.println("GenreFacade :: getGenreItemList");
        return genreService.getGenreItemList(command);
    }

    /**
     * 장르별 앨범 리스트
     * @param genreId 장르 idx
     * @param pageable 앨범 리스트 페이징을 위한 객체
     * @return 장르별 앨범 정보 리스트
     */
    public List<AlbumInfo.AlbumListInfo> retrieveGenreAlbumList(Long genreId, Pageable pageable) {
        System.out.println("AlbumFacade :: retrieveGenreAlbumList");
        return albumService.getGenreAlbumList(genreId, pageable);
    }

    /**
     * 전체 장르별 앨범 리스트
     * @param pageable 앨범 리스트 페이징을 위한 객체
     * @return 앨범 리스트가 담긴 장르 정보 리스트
     */
    public List<GenreInfo.Main> retrieveGenreAlbumListAll(Pageable pageable) {
        System.out.println("AlbumFacade :: retrieveGenreAlbumListAll");
        return albumService.getGenreAlbumListAll(pageable);
    }

    /**
     * 장르별 곡 리스트
     * @param genreId 장르 idx
     * @param type order 타입
     * @param pageable 앨범 리스트 페이징을 위한 객체
     * @return 장르별 곡 정보 리스트
     */
    public List<TrackInfo.TrackListInfo> retrieveGenreTrackList(Long genreId, String type, Pageable pageable) {
        System.out.println("AlbumFacade :: retrieveGenreAlbumList");
        return trackService.getGenreTrackList(genreId, type, pageable);
    }
}
