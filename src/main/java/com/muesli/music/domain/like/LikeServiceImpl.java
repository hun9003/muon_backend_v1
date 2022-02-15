package com.muesli.music.domain.like;

import com.muesli.music.common.exception.InvalidParamException;
import com.muesli.music.domain.user.token.UsertokenReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {
    private final LikeReader likeReader;
    private final LikeStore likeStore;
    private final UsertokenReader usertokenReader;

    @Override
    public LikeInfo findLikeBy(LikeCommand.RegisterLikeRequest command, String usertoken) {
        System.out.println("LikeServiceImpl :: findLikeBy");
        var usertokenInfo = usertokenReader.getUsertoken(usertoken);
        return new LikeInfo(likeReader.getLikeBy(usertokenInfo.getUserId(), command.getLikeableId(), command.getLikeableType()));
    }

    @Override
    @Transactional
    public void registerLike(LikeCommand.RegisterLikeRequest command, String usertoken) {
        System.out.println("LikeServiceImpl :: registerLike");
        var usertokenInfo = usertokenReader.getUsertoken(usertoken);
        var initLike = command.toEntity(usertokenInfo.getUserId());
        likeStore.store(initLike);
    }

    @Override
    @Transactional
    public void changeDoLike(LikeCommand.RegisterLikeRequest command, String usertoken) {
        System.out.println("LikeServiceImpl :: changeDoLike");
        var usertokenInfo = usertokenReader.getUsertoken(usertoken);
        var like = likeReader.getLikeBy(usertokenInfo.getUserId(), command.getLikeableId(), command.getLikeableType());
        like.doLike();
    }

    @Override
    @Transactional
    public void changeDisLike(Long likeId, String usertoken) {
        System.out.println("LikeServiceImpl :: changeDisLike");
        var user = usertokenReader.getUsertoken(usertoken);
        var like = likeReader.getLikyBy(likeId);
        if(Objects.equals(user.getUserId(), like.getUserId())) {
            like.doDisLike();
        } else {
            throw new InvalidParamException("토큰이 유효하지 않습니다.");
        }
    }
}
