package com.muesli.music.interfaces.user;

import com.muesli.music.application.user.UserFacade;
import com.muesli.music.common.response.CommonResponse;
import com.muesli.music.common.util.HashGenerator;
import com.muesli.music.common.util.TokenGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;

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
        System.out.println("UserApiController :: registerUser");
        var command = userDtoMapper.of(request);
        var userInfo = userFacade.registerUser(command);
        var response = new UserDto.RegisterResponse(userInfo);
        return CommonResponse.success(response);
    }

    /**
     * 로그인 POST
     * @param request
     * @return
     */
    @PostMapping("/login")
    public CommonResponse loginUser(@RequestBody @Valid UserDto.LoginUser request) {
        System.out.println("UserApiController :: loginUser");
        var command = request.toCommand();
        var usertokenInfo = userFacade.loginUser(command);
        var response = new UserDto.LoginResponse(usertokenInfo);
        var message = new HashMap<String, String>();
        message.put("dev", "로그인 완료");
        message.put("user", "로그인을 성공적으로 완료했습니다.");
        return CommonResponse.success(response, message);
    }

    /**
     * 이메일 인증
     * @param
     * @return 회원 정보
     */
    @GetMapping("/verification")
    public CommonResponse changeUserConfimed(@RequestParam("key")String key) throws Exception {
        System.out.println("UserApiController :: changeUserConfimed");
        String email = HashGenerator.getEmailByKey(key);
        userFacade.changeUserConfirmed(email);
        return CommonResponse.success("OK");
    }

    /**
     * 비밀번호 변경
     * @param usertoken
     * @param request
     * @return
     */
    @PutMapping("/modify/password")
    public CommonResponse changeUserPassword(@RequestHeader(value = "Authorization", defaultValue = "") String usertoken,
                                             @RequestBody @Valid UserDto.ChangePasswordRequest request) {
        System.out.println("UserApiController :: changeUserPassword");
        usertoken = TokenGenerator.getHeaderToken(usertoken);
        var command = request.toCommand();
        userFacade.changeUserPassword(command, usertoken);
        return CommonResponse.success("OK");
    }

}
