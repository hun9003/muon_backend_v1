package com.muesli.music.interfaces.like;

import com.muesli.music.application.like.LikeFacade;
import com.muesli.music.common.response.CommonResponse;
import com.muesli.music.common.util.TokenGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/likes")
public class LikeApiController {
    private final LikeFacade likeFacade;

    /**
     * 좋아요 POST
     * @param request
     * @param usertoken
     * @return
     */
    @PostMapping("/like")
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
    @PostMapping("/disLike/{id}")
    public CommonResponse doDisLike(@PathVariable("id") Long likeId, @RequestHeader(value="Authorization", defaultValue = "") String usertoken)
    {
        System.out.println("LikeApiController :: doDisLike");
        usertoken = TokenGenerator.getHeaderToken(usertoken);
        likeFacade.disLike(likeId, usertoken);
        return CommonResponse.success("OK");
    }
}
