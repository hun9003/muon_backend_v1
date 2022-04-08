package com.muesli.music.interfaces.like;

import com.muesli.music.application.like.LikeFacade;
import com.muesli.music.common.response.CommonResponse;
import com.muesli.music.common.util.TokenGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/likes")
public class LikeApiController {
    private final LikeFacade likeFacade;
    private final LikeDtoMapper likeDtoMapper;

    /**
     * 좋아요 POST
     * @param request
     * @param usertoken
     * @return
     */
    @PostMapping("")
    public CommonResponse doLike(@RequestBody @Valid LikeDto.RegisterLike request, @RequestHeader(value="Authorization", defaultValue = "") String usertoken) {
        System.out.println("LikeApiController :: doLike");
        usertoken = TokenGenerator.getHeaderToken(usertoken);
        var likeCommand = request.toCommand();
        likeFacade.doLike(likeCommand, usertoken);
        return CommonResponse.success("OK");
    }

    /**
     * 좋아요 취소
     * @param likeId
     * @return
     */
    @PutMapping("/{id}")
    public CommonResponse doDisLike(@PathVariable("id") Long likeId, @RequestHeader(value="Authorization", defaultValue = "") String usertoken)
    {
        System.out.println("LikeApiController :: doDisLike");
        usertoken = TokenGenerator.getHeaderToken(usertoken);
        likeFacade.disLike(likeId, usertoken);
        return CommonResponse.success("OK");
    }

    /**
     * 아이템 리스트 좋아요 여부 조회
     * @param request
     * @param usertoken
     * @return
     */
    @PostMapping("/show")
    public CommonResponse showLikeItem(@RequestBody @Valid LikeDto.LikeItemInfoList request, @RequestHeader(value="Authorization", defaultValue = "") String usertoken)
    {
        System.out.println("LikeApiController :: showLikeItem");
        usertoken = TokenGenerator.getHeaderToken(usertoken);
        var command = request.toCommand();
        var likeInfoList = likeFacade.showLikeItemList(command, usertoken);
        var response = new LikeDto.LikeInfoList(command.getType(), likeInfoList.stream().map(likeDtoMapper::of).collect(Collectors.toList()));
        return CommonResponse.success(response);
    }
}
