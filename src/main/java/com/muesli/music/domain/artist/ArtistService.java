package com.muesli.music.domain.artist;

import com.muesli.music.domain.search.SearchCommand;
import com.muesli.music.domain.user.UserInfo;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ArtistService {
    ArtistInfo.Main findArtistInfo(Long artistId, UserInfo.Main userInfo, Pageable pageable);

    List<ArtistInfo.Main> getLikeList(String token, Pageable pageable);

    List<ArtistInfo.SearchInfo> getSearchArtist(SearchCommand.SearchRequest command, Pageable pageable);
}
