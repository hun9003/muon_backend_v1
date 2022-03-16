package com.muesli.music.domain.like;

import com.muesli.music.common.exception.BaseException;
import com.muesli.music.common.exception.InvalidParamException;
import com.muesli.music.common.response.ErrorCode;
import com.muesli.music.domain.album.AlbumInfo;
import com.muesli.music.domain.album.AlbumReader;
import com.muesli.music.domain.artist.ArtistInfo;
import com.muesli.music.domain.artist.ArtistReader;
import com.muesli.music.domain.track.TrackInfo;
import com.muesli.music.domain.track.TrackReader;
import com.muesli.music.domain.user.token.UsertokenReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {
    private final LikeReader likeReader;
    private final LikeStore likeStore;
    private final UsertokenReader usertokenReader;

    /**
     * 해당 아이템을 좋아요 했는지 확인
     * @param command
     * @param token
     * @return 좋아요 정보
     */
    @Override
    public LikeInfo.Main findLikeBy(LikeCommand.RegisterLikeRequest command, String token) {
        System.out.println("LikeServiceImpl :: findLikeBy");
        var usertoken = usertokenReader.getUsertoken(token);
        if(usertoken.getUser() == null) throw new BaseException(ErrorCode.COMMON_PERMISSION_FALE);
        var likeCount = likeReader.getLikeCount(command.getLikeableId(), command.getLikeableType());
        return new LikeInfo.Main(likeReader.getLikeBy(usertoken.getUser().getId(), command.getLikeableId(), command.getLikeableType()), likeCount);
    }

    /**
     * 새로 좋아요를 했을때 DB에 저장
     * @param command
     * @param token
     */
    @Override
    @Transactional
    public void registerLike(LikeCommand.RegisterLikeRequest command, String token) {
        System.out.println("LikeServiceImpl :: registerLike");
        var usertoken = usertokenReader.getUsertoken(token);
        if(usertoken.getUser() == null) throw new BaseException(ErrorCode.COMMON_PERMISSION_FALE);
        var initLike = command.toEntity(usertoken.getUser().getId());
        likeStore.store(initLike);
    }

    /**
     * 이미 좋아요를 했던 아이템 상태변경 (좋아요)
     * @param command
     * @param token
     */
    @Override
    @Transactional
    public void changeDoLike(LikeCommand.RegisterLikeRequest command, String token) {
        System.out.println("LikeServiceImpl :: changeDoLike");
        var usertoken = usertokenReader.getUsertoken(token);
        if(usertoken.getUser() == null) throw new BaseException(ErrorCode.COMMON_PERMISSION_FALE);
        var like = likeReader.getLikeBy(usertoken.getUser().getId(), command.getLikeableId(), command.getLikeableType());
        like.doLike();
    }

    /**
     * 이미 좋아요를 했던 아이템 상태변경 (좋아요 취소)
     * @param likeId
     * @param token
     */
    @Override
    @Transactional
    public void changeDisLike(Long likeId, String token) {
        System.out.println("LikeServiceImpl :: changeDisLike");
        var usertoken = usertokenReader.getUsertoken(token);
        var like = likeReader.getLikeBy(likeId);
        if(usertoken.getUser() == null) throw new BaseException(ErrorCode.COMMON_PERMISSION_FALE);
        if(Objects.equals(usertoken.getUser().getId(), like.getUserId())) {
            like.doDisLike();
        } else {
            throw new BaseException(ErrorCode.COMMON_PERMISSION_FALE);
        }
    }

}
