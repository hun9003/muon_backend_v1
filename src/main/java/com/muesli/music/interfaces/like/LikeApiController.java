package com.muesli.music.interfaces.like;

import com.muesli.music.application.like.LikeFacade;
import com.muesli.music.common.exception.BaseException;
import com.muesli.music.common.response.CommonResponse;
import com.muesli.music.common.response.ErrorCode;
import com.muesli.music.common.util.TokenGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/like")
public class LikeApiController {
    private final LikeFacade likeFacade;
    private final LikeDtoMapper likeDtoMapper;

    /**
     * 좋아요 POST
     * @param request
     * @param usertoken
     * @return
     */
    @PostMapping
    public CommonResponse doLike(@RequestBody @Valid LikeDto.RegisterLike request, @RequestHeader(value="Authorization", defaultValue = "") String usertoken) {
        System.out.println("LikeApiController :: doLike");
        usertoken = TokenGenerator.getHeaderToken(usertoken);
        var likeCommand = request.toCommand();
        var likeInfo = likeFacade.doLike(likeCommand, usertoken);
        var response = likeDtoMapper.of(likeInfo);
        return CommonResponse.success(response);
    }

    /**
     * 좋아요 상태변경
     * @param request
     * @return
     */
    @PutMapping
    public CommonResponse doChangeLike(@RequestBody LikeDto.LikeInfo request, @RequestHeader(value="Authorization", defaultValue = "") String usertoken)
    {
        System.out.println("LikeApiController :: doChangeLike");
        usertoken = TokenGenerator.getHeaderToken(usertoken);
        if(request.getId() == null) throw new BaseException(ErrorCode.COMMON_INVALID_PARAMETER);
        var likeInfo = likeFacade.changeLike(request.getId(), usertoken);
        var response = likeDtoMapper.of(likeInfo);
        return CommonResponse.success(response);
    }

    /**
     * 아이템 리스트 좋아요 여부 조회
     * @param ids 좋아요 체크할 아이템 idx 배열 (id,id,id)
     * @param type 아이템 타입
     * @param usertoken
     * @return
     */
    @GetMapping("/show")
    public CommonResponse showLikeItem(@RequestParam(value = "ids") String ids, @RequestParam(value = "type") String type, @RequestHeader(value="Authorization", defaultValue = "") String usertoken)
    {
        System.out.println("LikeApiController :: showLikeItem");
        usertoken = TokenGenerator.getHeaderToken(usertoken);
        var request = new LikeDto.LikeItemInfoList(type, ids);
        var command = request.toCommand();
        var likeInfoList = likeFacade.showLikeItemList(command, usertoken);
        var response = new LikeDto.LikeInfoList(command.getType(), likeInfoList.stream().map(likeDtoMapper::of).collect(Collectors.toList()));
        return CommonResponse.success(response);
    }
}
