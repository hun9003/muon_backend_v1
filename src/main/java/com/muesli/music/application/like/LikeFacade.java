package com.muesli.music.application.like;

import com.muesli.music.domain.like.LikeCommand;
import com.muesli.music.domain.like.LikeInfo;
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
    public LikeInfo.Main doLike(LikeCommand.RegisterLikeRequest command, String token) {
        System.out.println("LikeFacade :: doLike");
        usertokenService.checkUsertoken(token);
        var likeInfo = likeService.findLikeBy(command, token);
        if (likeInfo.getId() == null) {
            likeInfo = likeService.registerLike(command, token);
        } else if (likeInfo.getIsLike() == 1 ) {
            likeInfo = likeService.changeDisLike(likeInfo.getId(), token);
        } else {
            likeInfo = likeService.changeDoLike(likeInfo.getId(), token);
        }
        return likeInfo;
    }

    /**
     * 좋아요 상태변경
     * @param likeId
     * @param token
     */
    public LikeInfo.Main changeLike(Long likeId, String token) {
        System.out.println("LikeFacade :: disLike");
        usertokenService.checkUsertoken(token);
        var likeInfo = likeService.getLike(likeId);
        if(likeInfo.getIsLike() == 1) {
            likeInfo = likeService.changeDisLike(likeId, token);
        } else {
            likeInfo = likeService.changeDoLike(likeId, token);
        }
        return likeInfo;
    }

    /**
     * 해당 아이템들 좋아요 여부 파악
     * @param command
     * @param token
     * @return
     */
    public List<LikeInfo.Main> showLikeItemList(LikeCommand.ShowLikeListRequest command, String token) {
        System.out.println("LikeFacade :: showLikeItemList");
        usertokenService.checkUsertoken(token);
        return likeService.findLikeBy(command, token);
    }
}
