package com.muesli.music.domain.like;

import com.muesli.music.common.exception.InvalidParamException;
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
    private final TrackReader trackReader;
    private final AlbumReader albumReader;
    private final ArtistReader artistReader;

    /**
     * 해당 아이템을 좋아요 했는지 확인
     * @param command
     * @param usertoken
     * @return 좋아요 정보
     */
    @Override
    public LikeInfo.Main findLikeBy(LikeCommand.RegisterLikeRequest command, String usertoken) {
        System.out.println("LikeServiceImpl :: findLikeBy");
        var usertokenInfo = usertokenReader.getUsertoken(usertoken);
        var likeCount = likeReader.getLikeCount(command.getLikeableId(), command.getLikeableType());
        return new LikeInfo.Main(likeReader.getLikeBy(usertokenInfo.getUser().getId(), command.getLikeableId(), command.getLikeableType()), likeCount);
    }

    /**
     * 새로 좋아요를 했을때 DB에 저장
     * @param command
     * @param usertoken
     */
    @Override
    @Transactional
    public void registerLike(LikeCommand.RegisterLikeRequest command, String usertoken) {
        System.out.println("LikeServiceImpl :: registerLike");
        var usertokenInfo = usertokenReader.getUsertoken(usertoken);
        var initLike = command.toEntity(usertokenInfo.getUser().getId());
        likeStore.store(initLike);
    }

    /**
     * 이미 좋아요를 했던 아이템 상태변경 (좋아요)
     * @param command
     * @param usertoken
     */
    @Override
    @Transactional
    public void changeDoLike(LikeCommand.RegisterLikeRequest command, String usertoken) {
        System.out.println("LikeServiceImpl :: changeDoLike");
        var usertokenInfo = usertokenReader.getUsertoken(usertoken);
        var like = likeReader.getLikeBy(usertokenInfo.getUser().getId(), command.getLikeableId(), command.getLikeableType());
        like.doLike();
    }

    /**
     * 이미 좋아요를 했던 아이템 상태변경 (좋아요 취소)
     * @param likeId
     * @param usertoken
     */
    @Override
    @Transactional
    public void changeDisLike(Long likeId, String usertoken) {
        System.out.println("LikeServiceImpl :: changeDisLike");
        var user = usertokenReader.getUsertoken(usertoken);
        var like = likeReader.getLikeBy(likeId);
        if(Objects.equals(user.getUser().getId(), like.getUserId())) {
            like.doDisLike();
        } else {
            throw new InvalidParamException("토큰이 유효하지 않습니다.");
        }
    }

}
