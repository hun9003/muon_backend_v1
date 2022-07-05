package com.muesli.music.interfaces.user;

import com.muesli.music.application.user.UserFacade;
import com.muesli.music.common.exception.BaseException;
import com.muesli.music.common.response.CommonResponse;
import com.muesli.music.common.response.ErrorCode;
import com.muesli.music.common.util.HashGenerator;
import com.muesli.music.common.util.MailController;
import com.muesli.music.common.util.TokenGenerator;
import com.muesli.music.domain.user.UserCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.muesli.music.common.util.Message.*;

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
        var message = makeResponseMessage(Developer.SUCCESS_SIGNUP);
        return CommonResponse.success(response, message);
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
        var message = makeResponseMessage(Developer.SUCCESS_SIGNIN);
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
        var message = makeResponseInfoMessage(Developer.INFO_EMAIL_VERIFICATION, User.INFO_EMAIL_VERIFICATION);
        return CommonResponse.success(null, message);
    }

    /**
     * 이메일 전송
     * @param
     * @return 회원 정보
     */
    @PostMapping("/mail/send")
    public CommonResponse sendMail(@RequestParam("email")String email) {
        System.out.println("UserApiController :: sendMail");

        var userInfo = userFacade.getUserByEmail(email);
        try {
            MailController.sendMail(userInfo.getEmail(), userInfo.getUsername(), MailController.REGISTER);
        } catch (Exception e) {
            throw new BaseException(ErrorCode.COMMON_SYSTEM_ERROR);
        }

        var message = makeResponseSuccessMessage(Developer.SUCCESS_EMAIL_VERIFICATION, User.SUCCESS_EMAIL_VERIFICATION);
        return CommonResponse.success(null, message);
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
        var message = makeResponseSuccessMessage(Developer.SUCCESS_CHANGE_PASSWORD, User.SUCCESS_CHANGE_PASSWORD);
        return CommonResponse.success(null, message);
    }

    /**
     * 소셜 로그인 GET
     * @param accessToken 엑세스 토큰
     * @return
     */
    @GetMapping("/login/social")
    public CommonResponse socialLoginUser(@RequestParam(name="access_token") String accessToken, @RequestParam(name="auth_type") String authType) {
        System.out.println("UserApiController :: socialLoginUser");
        var socialLoginCommand = new UserCommand.SocialLoginRequest(accessToken, authType);
        var usertokenInfo = userFacade.socialLoginUser(socialLoginCommand);
        var response = new UserDto.LoginResponse(usertokenInfo);
        var message = makeResponseMessage(Developer.SUCCESS_SIGNIN);
        return CommonResponse.success(response, message);
    }

}
