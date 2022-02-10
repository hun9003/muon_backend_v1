package com.muesli.music.application.user;

import com.muesli.music.common.exception.BaseException;
import com.muesli.music.common.exception.IllegalStatusException;
import com.muesli.music.common.exception.InvalidParamException;
import com.muesli.music.common.response.ErrorCode;
import com.muesli.music.common.util.HashGenerator;
import com.muesli.music.domain.user.UserCommand;
import com.muesli.music.domain.user.UserInfo;
import com.muesli.music.domain.user.UserService;
import com.muesli.music.domain.user.token.Usertoken;
import com.muesli.music.domain.user.token.UsertokenCommand;
import com.muesli.music.domain.user.token.UsertokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserFacade {
    private final UserService userService;
    private final UsertokenService usertokenService;

    /**
     * 회원가입
     * @param command
     * @return
     */
    public UserInfo.Main registerUser(UserCommand command) {
        System.out.println("UserFacade :: registerUser");
        // 1. 이메일로 가입한 유저 검색
        var userinfo = userService.findUserInfo(command.getEmail());

        // 2. 가입한 유저가 있으면 중복오류 던지기
        if (userinfo.getId() != null) throw new BaseException(ErrorCode.COMMON_DUPLICATION_EMAIL);

        // 3. 가입 처리
        return userService.registerUser(command);
    }

    /**
     * 유저 로그인
     * @param email 이메일
     * @param password 패스워드
     * @return
     */
    public UserInfo loginUser(String email, String password) {
        System.out.println("UserFacade :: loginUser");
        // 1. 아이디, 비밀번호가 일치하는지 확인
        var userinfo = userService.loginUser(email, HashGenerator.hashPassword(email, password));

        // 2. 이메일 인증이 되어있지 않다면 오류 던지기
        if (userinfo.getConfirmd() == 0) throw new IllegalStatusException("메일 인증이 완료되지 않았습니다.");

        var userTokenInfo = usertokenService.registerUsertoken(UsertokenCommand.R);

        return userinfo;
    }

}
