package com.muesli.music.interfaces.like;

import com.muesli.music.domain.album.AlbumInfo;

public interface LikeDtoMapper {

    // find
    LikeDto.Main of(AlbumInfo.LikeInfo likeInfo);
}
