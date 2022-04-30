package com.muesli.music.domain.like;

import com.muesli.music.common.exception.BaseException;
import com.muesli.music.common.response.ErrorCode;
import com.muesli.music.common.util.Constant;
import com.muesli.music.domain.user.token.UsertokenReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
     * @param command 좋아요 정보가 담긴 데이터 객체
     * @param token 유저 토큰
     * @return 좋아요 정보
     */
    @Override
    public LikeInfo.Main findLikeBy(LikeCommand.RegisterLikeRequest command, String token) {
        System.out.println("LikeServiceImpl :: findLikeBy");
        var usertoken = usertokenReader.getUsertoken(token);
        if(usertoken.getUser() == null) throw new BaseException(ErrorCode.USER_PERMISSION_FALE);
        var likeCount = likeReader.getLikeCount(command.getLikeableId(), command.getLikeableType());
        return new LikeInfo.Main(likeReader.getLikeBy(usertoken.getUser().getId(), command.getLikeableId(), command.getLikeableType()), likeCount, command.getLikeableId());
    }

    /**
     * 해당 아이템을 좋아요 했는지 확인 (리스트)
     * @param command 아이템 리스트가 담긴 데이터 객체
     * @param token 유저 토큰
     * @return 좋아요 정보 리스트
     */
    @Override
    public List<LikeInfo.Main> findLikeBy(LikeCommand.ShowLikeListRequest command, String token) {
        System.out.println("LikeServiceImpl :: findLikeBy");
        var usertoken = usertokenReader.getUsertoken(token);
        if(usertoken.getUser() == null) throw new BaseException(ErrorCode.USER_PERMISSION_FALE);

        // 좋아요 정보 조회를 위한 아이템 타입 분류
        var type = "";
        switch (command.getType()) {
            case Constant.Item.ALBUM: type = "Album"; break;
            case Constant.Item.ARTIST: type = "Artist"; break;
            case Constant.Item.TRACK: type = "Track"; break;
            case Constant.Item.PLAYLIST: type = "Playlist"; break;
            default: throw new BaseException(ErrorCode.ITEM_BAD_TYPE);
        }

        var likeInfoList = new ArrayList<LikeInfo.Main>();
        for (int i = 0; i < command.getIds().size(); i++) {
            var likeCount = likeReader.getLikeCount(command.getIds().get(i), type);
            likeInfoList.add(new LikeInfo.Main(likeReader.getLikeBy(usertoken.getUser().getId(), command.getIds().get(i), type), likeCount, command.getIds().get(i)));
        }
        return likeInfoList;
    }

    /**
     * 새로 좋아요를 했을 때 DB에 저장
     * @param command 아이템 리스트가 담긴 데이터 객체
     * @param token 유저 토큰
     * @return 좋아요 정보
     */
    @Override
    @Transactional
    public LikeInfo.Main registerLike(LikeCommand.RegisterLikeRequest command, String token) {
        System.out.println("LikeServiceImpl :: registerLike");
        var usertoken = usertokenReader.getUsertoken(token);
        if(usertoken.getUser() == null) throw new BaseException(ErrorCode.USER_PERMISSION_FALE);
        var initLike = command.toEntity(usertoken.getUser().getId());
        var like = likeStore.store(initLike);
        var likeInfo = new LikeInfo.Main(like);
        var count = likeReader.getLikeCount(likeInfo.getLikeableId(), likeInfo.getLikeableType());
        likeInfo.setLikeCount(count);
        return likeInfo;
    }

    /**
     * 이미 좋아요를 했던 아이템 상태변경 (좋아요)
     * @param likeId 좋아요 idx
     * @param token 유저 정보
     * @return 좋아요 정보
     */
    @Override
    @Transactional
    public LikeInfo.Main changeDoLike(Long likeId, String token) {
        System.out.println("LikeServiceImpl :: changeDoLike");
        var usertoken = usertokenReader.getUsertoken(token);
        if(usertoken.getUser() == null) throw new BaseException(ErrorCode.USER_PERMISSION_FALE);
        var like = likeReader.getLikeBy(likeId);
        if(Objects.equals(usertoken.getUser().getId(), like.getUserId())) {
            like.doLike();
        } else {
            throw new BaseException(ErrorCode.USER_PERMISSION_FALE);
        }
        var likeInfo = new LikeInfo.Main(like);
        var count = likeReader.getLikeCount(likeInfo.getLikeableId(), likeInfo.getLikeableType());
        likeInfo.setLikeCount(count);
        return likeInfo;
    }

    /**
     * 이미 좋아요를 했던 아이템 상태변경 (좋아요 취소)
     * @param likeId 좋아요 idx
     * @param token 유저 토큰
     * @return 좋아요 정보
     */
    @Override
    @Transactional
    public LikeInfo.Main changeDisLike(Long likeId, String token) {
        System.out.println("LikeServiceImpl :: changeDisLike");
        var usertoken = usertokenReader.getUsertoken(token);
        var like = likeReader.getLikeBy(likeId);
        if(usertoken.getUser() == null) throw new BaseException(ErrorCode.USER_PERMISSION_FALE);
        if(Objects.equals(usertoken.getUser().getId(), like.getUserId())) {
            like.doDisLike();
        } else {
            throw new BaseException(ErrorCode.USER_PERMISSION_FALE);
        }
        var likeInfo = new LikeInfo.Main(like);
        var count = likeReader.getLikeCount(likeInfo.getLikeableId(), likeInfo.getLikeableType());
        likeInfo.setLikeCount(count);
        return likeInfo;
    }

    /**
     * 좋아요 상태 확인
     * @param likeId 좋아요 idx
     * @return 좋아요 정보
     */
    @Override
    public LikeInfo.Main getLike(Long likeId) {
        System.out.println("LikeServiceImpl :: getLike");
        return new LikeInfo.Main(likeReader.getLikeBy(likeId));
    }
}
