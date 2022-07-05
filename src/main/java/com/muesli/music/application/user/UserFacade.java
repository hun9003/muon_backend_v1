package com.muesli.music.application.user;

import com.muesli.music.common.exception.BaseException;
import com.muesli.music.common.exception.IllegalStatusException;
import com.muesli.music.common.response.ErrorCode;
import com.muesli.music.domain.user.UserCommand;
import com.muesli.music.domain.user.UserInfo;
import com.muesli.music.domain.user.UserService;
import com.muesli.music.domain.user.token.UsertokenCommand;
import com.muesli.music.domain.user.token.UsertokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
/*
    요구사항 정의
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserFacade {
    private final UserService userService;
    private final UsertokenService usertokenService;

    /**
     * 회원가입
     * @param command 회원가입을 위한 유저 데이터 객체
     * @return 가입 된 유저 정보
     */
    public UserInfo.Main registerUser(UserCommand.RegisterUserRequest command) {
        System.out.println("UserFacade :: registerUser");
        // 1. 이메일로 가입한 유저 검색
        var userinfo = userService.findUserInfo(command.getEmail());

        // 2. 가입한 유저가 있으면 중복 오류 던지기
        if (userinfo.getId() != null) throw new BaseException(ErrorCode.USER_DUPLICATION_EMAIL);

        // 3. 닉네임이 중복 되는지 검색
//        if (userService.isDuplicateUsername(command.getUsername())) throw new BaseException(ErrorCode.USER_DUPLICATION_USERNAME);

        //  4. 가입 처리
        var user = userService.registerUser(command);

        // 5. 이메일 인증 메일 보내기
        // 가입 축하 메일 보내기
//        try {
//            MailController.sendMail(user.getEmail(), user.getUsername(), MailController.REGISTER);
//        } catch (Exception e) {
//            throw new BaseException(ErrorCode.COMMON_SYSTEM_ERROR);
//        }
        

        return user;

    }


    /**
     * 유저 로그인
     * @param command
     * @return 로그인한 유저 정보
     */
    public UserInfo.UsertokenInfo loginUser(UserCommand.LoginUserRequest command) {
        System.out.println("UserFacade :: loginUser");
        // 1. 아이디, 비밀번호가 일치하는지 확인 후 일치하면 토큰 생성
        var usertokenInfo = userService.loginUser(command);

        // 2. 이메일 인증이 되어있지 않다면 오류 던지기
        if (usertokenInfo.getUserInfo().getConfirmed() == 0) throw new IllegalStatusException("메일 인증이 완료되지 않았습니다.");

        // 3. uuid 세팅이 되어있지 않다면 세팅하기
        if (usertokenInfo.getUserInfo().getUuid() == null) userService.registerUserUuid(usertokenInfo.getUserInfo().getEmail());

        return usertokenInfo;
    }

    /**
     * 유저 토큰 생성
     * @param command 토큰 생성을 위한 데이터 객체
     * @return 유저 토큰 정보
     */
    public UserInfo.UsertokenInfo registerUsertoken(UsertokenCommand command) {
        System.out.println("UserFacade :: registerUsertoken");
        return usertokenService.registerUsertoken(command);
    }

    /**
     * 이메일 인증
     * @param email 이메일
     */
    public void changeUserConfirmed(String email) {
        System.out.println("UserFacade :: changeUserConfirmed");
        userService.changeConfirmed(email);
    }

    /**
     * 비밀번호 변경
     * @param command 비밀번호 변경을 위한 데이터 객체
     * @param token  유저 토큰
     */
    public void changeUserPassword(UserCommand.ChangeUserPassword command, String token) {
        System.out.println("UserFacade :: changeUserPassword");
        var usertoken = usertokenService.findUsertokenInfo(token);
        userService.changePassword(command, usertoken.getUserInfo().getEmail());
    }

    /**
     * 이메일로 유저 정보 호출
     * @param email
     * @return
     */
    public UserInfo.Main getUserByEmail(String email) {
        System.out.println("UserFacade :: registerUserByEmail");
        return userService.findUserInfo(email);
    }

    public UserInfo.UsertokenInfo socialLoginUser(UserCommand.SocialLoginRequest socialLoginCommand) {
        System.out.println("UserFacade :: socialLoginUser");
        // 소셜 로그인 회원정보 가져오기
        var user = userService.getSocialUserInfo(socialLoginCommand);

        // 회원가입이 되어있지 않다면 회원가입 시키기
        if(userService.findUserInfo(user.getEmail()).getId() == null) {
            userService.registerSocialUser(user);
        }

        var userInfo = userService.findUserInfo(user.getEmail());

        // 토큰 생성
        var usertokenInfo = userService.socialLoginUser(userInfo);

        // uuid 세팅이 되어있지 않다면 세팅하기
        if (usertokenInfo.getUserInfo().getUuid() == null) userService.registerUserUuid(usertokenInfo.getUserInfo().getEmail());

        return usertokenInfo;
    }
}
