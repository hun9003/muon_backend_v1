package com.muesli.music.domain.search;

import com.muesli.music.domain.album.AlbumReader;
import com.muesli.music.domain.artist.ArtistReader;
import com.muesli.music.domain.track.TrackReader;
import com.muesli.music.domain.track.lyrics.LyricsReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {
    TrackReader trackReader;
    AlbumReader albumReader;
    ArtistReader artistReader;

    @Override
    public SearchInfo.SearchTrack getSearchTrack(SearchCommand.SearchRequest command, Pageable pageable) {
        System.out.println("SearchServiceImpl :: getSearchTrack");
        return null;
    }

    @Override
    public SearchInfo.SearchAlbum getSearchAlbum(SearchCommand.SearchRequest command, Pageable pageable) {
        System.out.println("SearchServiceImpl :: getSearchAlbum");
        return null;
    }

    @Override
    public SearchInfo.SearchArtist getSearchArtist(SearchCommand.SearchRequest command, Pageable pageable) {
        System.out.println("SearchServiceImpl :: getSearchArtist");
        return null;
    }

    @Override
    public SearchInfo.SearchLyrics getSearchLyrics(SearchCommand.SearchRequest command, Pageable pageable) {
        System.out.println("SearchServiceImpl :: getSearchLyrics");
        return null;
    }
}
