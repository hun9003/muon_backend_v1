package com.muesli.music.application.like;

import com.muesli.music.domain.like.LikeCommand;
import com.muesli.music.domain.like.LikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LikeFacade {
    private final LikeService likeService;

    public void doLike(LikeCommand.RegisterLikeRequest command, String usertoken) {
        System.out.println("LikeFacade :: doLike");
        var likeInfo = likeService.findLikeBy(command, usertoken);
        System.out.println(likeInfo.toString());
        if (likeInfo.getId() == null) {
            likeService.registerLike(command, usertoken);
        } else {
            likeService.changeDoLike(command, usertoken);
        }
    }

    public void disLike(Long likeId, String usertoken) {
        System.out.println("LikeFacade :: disLike");
        likeService.changeDisLike(likeId, usertoken);
    }
}
