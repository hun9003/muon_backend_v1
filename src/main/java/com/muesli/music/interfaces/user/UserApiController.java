package com.muesli.music.interfaces.user;

import com.muesli.music.application.user.UserFacade;
import com.muesli.music.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserApiController {
    private final UserDtoMapper userDtoMapper;
    private final UserFacade userFacade;

    /**
     * 회원가입 POST
     * @param request 회원 정보
     * @return 회원 정보
     */
    @PostMapping
    public CommonResponse registerUser(@RequestBody @Valid UserDto.RegisterUser request) {
        var command = userDtoMapper.of(request);
        var userInfo = userFacade.registerUser(command);
        var response = new UserDto.RegisterResponse(userInfo);
        return CommonResponse.success(response);
    }

    @PostMapping("/login")
    public CommonResponse loginUser(@RequestBody @Valid UserDto.LoginUser request) {
        var usertokenInfo = userFacade.loginUser(request.getEmail(), request.getPassword());
        var response = userDtoMapper.of(usertokenInfo);
        return CommonResponse.success(response);
    }

    /**
     * 이메일 인증 POST
     * @param
     * @return 회원 정보
     */
    @GetMapping("/verification")
    public CommonResponse registerUser(@RequestParam("key")String key) {

        return null;
    }

}
