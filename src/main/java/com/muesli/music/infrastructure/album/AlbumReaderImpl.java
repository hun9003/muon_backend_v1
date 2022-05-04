package com.muesli.music.infrastructure.album;

import com.google.common.collect.Lists;
import com.muesli.music.common.exception.EntityNotFoundException;
import com.muesli.music.common.exception.InvalidParamException;
import com.muesli.music.common.util.Constant;
import com.muesli.music.domain.album.Album;
import com.muesli.music.domain.album.AlbumReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class AlbumReaderImpl implements AlbumReader {
    private final AlbumRepository albumRepository;

    @Override
    public Album getAlbumBy(Long albumId) {
        System.out.println("AlbumReaderImpl :: getAlbumListByArtist");
        return albumRepository.findAlbumById(albumId)
                .orElseThrow(EntityNotFoundException::new);
    }


    @Override
    public int getAlbumListByArtistCount(Long artistId) {
        System.out.println("AlbumReaderImpl :: getAlbumListByArtistCount");
        return albumRepository.countAlbumByArtistId(artistId).orElse(0);
    }

    @Override
    public List<Map<String, Object>> getAlbumListByArtist(Long artistId, int start, int end) {
        System.out.println("AlbumReaderImpl :: getAlbumListByArtist");
        return albumRepository.findAlbumByArtist(artistId, start, end).orElse(Lists.newArrayList());
    }

    @Override
    public int getAlbumLikeListCount(Long userId) {
        System.out.println("AlbumReaderImpl :: getAlbumLikeListCount");
        return albumRepository.countLikeList(userId).orElse(0);
    }

    @Override
    public List<Map<String, Object>> getAlbumLikeList(Long userId, int start, int end) {
        System.out.println("AlbumReaderImpl :: getAlbumLikeList");
        return albumRepository.findLikeList(userId, start, end).orElse(Lists.newArrayList());
    }

    @Override
    public List<Map<String, Object>> getNewAlbum(int start, int end) {
        System.out.println("AlbumReaderImpl :: getNewAlbum");
        return albumRepository.findNewAlbum(start, end).orElseThrow(InvalidParamException::new);
    }

    @Override
    public int getSearchAlbumCount(String keyword) {
        System.out.println("AlbumReaderImpl :: getSearchAlbumCount");
        return albumRepository.findSearchAlbumCount(keyword).orElse(0);
    }

    @Override
    public List<Map<String, Object>> getSearchAlbum(String keyword, String type, int start, int end) {
        System.out.println("AlbumReaderImpl :: getSearchAlbum");
        List<Map<String, Object>> albumList;
        switch (type) {
            case Constant.Order.SIMILAR : albumList = albumRepository.findSearchAlbumOrderBySimilar(keyword, start, end).orElse(new ArrayList<>()); break;
            case Constant.Order.NEWEST : albumList = albumRepository.findSearchAlbumOrderByNewest(keyword, start, end).orElse(new ArrayList<>()); break;
            case Constant.Order.POPULARITY :
            default: albumList = albumRepository.findSearchAlbumOrderByPopularity(keyword, start, end).orElse(new ArrayList<>());
        }
        return albumList;
    }

    @Override
    public List<Map<String, Object>> getGenreAlbumList(Long genreId, Pageable pageable) {
        System.out.println("AlbumReaderImpl :: getGenreAlbumList");
        return albumRepository.findGenreAlbumList(genreId, pageable.getPageSize()).orElse(Lists.newArrayList());
    }

    @Override
    public int getChannelAlbumCount(Long channelId) {
        System.out.println("AlbumReaderImpl :: getChannelAlbumCount");
        return albumRepository.countChannelAlbum(channelId).orElse(0);
    }

    @Override
    public List<Map<String, Object>> getChannelAlbumList(Long channelId, int startNum, int endNum) {
        System.out.println("AlbumReaderImpl :: getChannelAlbumList");
        return albumRepository.findChannelAlbum(channelId, startNum, endNum).orElse(Lists.newArrayList());
    }
}
