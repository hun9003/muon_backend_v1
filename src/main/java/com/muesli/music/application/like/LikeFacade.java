package com.muesli.music.application.like;

import com.muesli.music.domain.like.LikeCommand;
import com.muesli.music.domain.like.LikeService;
import com.muesli.music.domain.user.token.UsertokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LikeFacade {
    private final LikeService likeService;
    private final UsertokenService usertokenService;

    /**
     * 좋아요
     * @param command
     * @param token
     */
    public void doLike(LikeCommand.RegisterLikeRequest command, String token) {
        System.out.println("LikeFacade :: doLike");
        usertokenService.checkUsertoken(token);
        var likeInfo = likeService.findLikeBy(command, token);
        if (likeInfo.getId() == null) {
            likeService.registerLike(command, token);
        } else {
            likeService.changeDoLike(command, token);
        }
    }

    /**
     * 좋아요 취소
     * @param likeId
     * @param token
     */
    public void disLike(Long likeId, String token) {
        System.out.println("LikeFacade :: disLike");
        usertokenService.checkUsertoken(token);
        likeService.changeDisLike(likeId, token);
    }
}
