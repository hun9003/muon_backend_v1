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
     * @param command 좋아요 처리를 위한 데이터 객체
     * @param token 유저 토큰
     * @return 좋아요 정보
     */
    public LikeInfo.Main doLike(LikeCommand.RegisterLikeRequest command, String token) {
        System.out.println("LikeFacade :: doLike");
        // 유저 토큰 유호셩 검사
        usertokenService.checkUsertoken(token);
        
        // 사용자의 해당 아이템 좋아요 정보가 존재하는지 검사
        var likeInfo = likeService.findLikeBy(command, token);
        if (likeInfo.getId() == null) {
            // 좋아요 정보가 존재하지 않을 시 새로 생성
            likeInfo = likeService.registerLike(command, token);
        } else if (likeInfo.getIsLike() == 1 ) {
            // 좋아요 정보가 존재 하며 현재 좋아요한 상태일 시 좋아요 취소 상태로 변경
            likeInfo = likeService.changeDisLike(likeInfo.getId(), token);
        } else {
            // 좋아요 정보가 존재하며 현재 좋아요 취소 상태일 시 좋아요 상태로 변경
            likeInfo = likeService.changeDoLike(likeInfo.getId(), token);
        }
        return likeInfo;
    }

    /**
     * 좋아요 상태변경
     * @param likeId 좋아요 PK
     * @param token 유저 토큰
     * @return 좋아요 정보
     */
    public LikeInfo.Main changeLike(Long likeId, String token) {
        System.out.println("LikeFacade :: changeLike");
        // 유저 토큰 유효성 검사
        usertokenService.checkUsertoken(token);

        // 사용자의 해당 아이템 좋아요 정보가 존재하는지 검사
        var likeInfo = likeService.getLike(likeId);
        if(likeInfo.getIsLike() == 1) {
            // 좋아요 정보가 존재 하며 현재 좋아요한 상태일 시 좋아요 취소 상태로 변경
            likeInfo = likeService.changeDisLike(likeId, token);
        } else {
            // 좋아요 정보가 존재하며 현재 좋아요 취소 상태일 시 좋아요 상태로 변경
            likeInfo = likeService.changeDoLike(likeId, token);
        }
        return likeInfo;
    }

    /**
     * 해당 아이템들 좋아요 여부 파악
     * @param command 좋아요를 검사할 데이터 리스트 객체
     * @param token 유저 토큰
     * @return 좋아요 정보 리스트
     */
    public List<LikeInfo.Main> showLikeItemList(LikeCommand.ShowLikeListRequest command, String token) {
        System.out.println("LikeFacade :: showLikeItemList");
        // 유저 토큰 유효성 검사
        usertokenService.checkUsertoken(token);
        return likeService.findLikeBy(command, token);
    }
}
