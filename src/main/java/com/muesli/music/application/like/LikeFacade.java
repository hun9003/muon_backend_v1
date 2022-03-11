package com.muesli.music.application.like;

import com.muesli.music.domain.like.LikeCommand;
import com.muesli.music.domain.like.LikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LikeFacade {
    private final LikeService likeService;

    /**
     * 좋아요
     * @param command
     * @param usertoken
     */
    public void doLike(LikeCommand.RegisterLikeRequest command, String usertoken) {
        System.out.println("LikeFacade :: doLike");
        var likeInfo = likeService.findLikeBy(command, usertoken);
        if (likeInfo.getId() == null) {
            likeService.registerLike(command, usertoken);
        } else {
            likeService.changeDoLike(command, usertoken);
        }
    }

    /**
     * 좋아요 취소
     * @param likeId
     * @param usertoken
     */
    public void disLike(Long likeId, String usertoken) {
        System.out.println("LikeFacade :: disLike");
        likeService.changeDisLike(likeId, usertoken);
    }
}