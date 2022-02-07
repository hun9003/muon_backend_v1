package com.muesli.music.application.album;

import com.muesli.music.domain.album.AlbumInfo;
import com.muesli.music.domain.album.AlbumService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlbumFacade {
    private final AlbumService albumService;

    public AlbumInfo.Main findAlbumInfo(Long albumId) {
        return albumService.findAlbumInfo(albumId);
    }
}
