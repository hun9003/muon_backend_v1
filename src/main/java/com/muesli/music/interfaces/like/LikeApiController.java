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
    private final LikeDtoMapper likeDtoMapper;
    private final LikeFacade likeFacade;


    @GetMapping("/likeables/track")
    public CommonResponse retrieveLikeListInfo(@RequestParam("likeableType") String likeableType,
                                               @RequestHeader(value="Authorization", defaultValue = "") String usertoken) {
        System.out.println("LikeApiController :: retrieveLikeListInfo");
        usertoken = TokenGenerator.getHeaderToken(usertoken);
        var likeInfoList = likeFacade.retrieveLikeInfoList(likeableType, usertoken);
        var likeInfoDtoList = likeInfoList.stream().map(likeDtoMapper::ofItem).collect(Collectors.toList());
        var response = new LikeDto.LikeInfoList(likeInfoDtoList);
        return CommonResponse.success(response);
    }
    /**
     * 좋아요 POST
     * @param request
     * @param usertoken
     * @return
     */
    @PostMapping("/like")
    public CommonResponse doLike(@RequestBody @Valid LikeDto.RegisterLike request, @RequestHeader(value="Authorization", defaultValue = "") String usertoken) {
        System.out.println("LikeApiController :: doLike");
        var likeCommand = likeDtoMapper.of(request);
        usertoken = TokenGenerator.getHeaderToken(usertoken);
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
