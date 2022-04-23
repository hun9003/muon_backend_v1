package com.muesli.music.application.user;

import com.muesli.music.common.exception.BaseException;
import com.muesli.music.common.response.ErrorCode;
import com.muesli.music.common.util.HashGenerator;
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
        if (userinfo.getId() != null) throw new BaseException(ErrorCode.COMMON_DUPLICATION_EMAIL);

        // 3. 가입 처리
        var user = userService.registerUser(command);

        // 4. 이메일 인증 메일 보내기
        // 가입 축하 메일 보내기
//        try {
//            var registerTemplate = new MailController.MailTemplate(user.getEmail(), user.getUsername());
//            MailController.sendMail(registerTemplate.getTitle(), registerTemplate.getContent(), registerTemplate.getEmail());
//        } catch (Exception e) {
//            throw new BaseException(ErrorCode.COMMON_SYSTEM_ERROR);
//        }
        

        return user;

    }


    /**
     * 유저 로그인
     * @param email 이메일
     * @param password 패스워드
     * @return 로그인한 유저 정보
     */
    public UserInfo.UsertokenInfo loginUser(String email, String password) {
        System.out.println("UserFacade :: loginUser");
        // 1. 아이디, 비밀번호가 일치하는지 확인 후 일치하면 토큰 생성
        var usertokenInfo = userService.loginUser(email, HashGenerator.hashPassword(email, password));

        // 2. 이메일 인증이 되어있지 않다면 오류 던지기
//        if (userinfo.getConfirmed() == 0) throw new IllegalStatusException("메일 인증이 완료되지 않았습니다.");

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


}
