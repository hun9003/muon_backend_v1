package com.muesli.music.domain.album;

import com.muesli.music.domain.like.LikeReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlbumServiceImpl implements AlbumService {
    private final AlbumReader albumReader;
    private final LikeReader likeReader;

    @Override
    @Transactional(readOnly = true)
    public AlbumInfo.Main findAlbumInfo(Long albumId) {
        var album = albumReader.getAlbumBy(albumId);
        var trackList = albumReader.getTrackList(album);
        var like = new AlbumInfo.LikeInfo(likeReader.getLikeBy(1L, albumId, "App\\Album"));
        return new AlbumInfo.Main(album, trackList, like);
    }
}
