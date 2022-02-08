package com.muesli.music.interfaces.user;

import com.muesli.music.application.user.UserFacade;
import com.muesli.music.common.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserApiController {
    private final UserDtoMapper userDtoMapper;
    private final UserFacade userFacade;

    @PostMapping
    public CommonResponse registerUser(@RequestBody @Valid UserDto.RegisterUser request) {
        var command = userDtoMapper.of(request);
        var userInfo = userFacade.regisgerUser(command);
        var response = new UserDto.RegisterResponse(userInfo);
        return CommonResponse.success(response);
    }

}
