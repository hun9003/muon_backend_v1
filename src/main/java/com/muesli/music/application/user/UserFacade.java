package com.muesli.music.application.user;

import com.muesli.music.common.exception.BaseException;
import com.muesli.music.common.response.ErrorCode;
import com.muesli.music.domain.user.UserCommand;
import com.muesli.music.domain.user.UserInfo;
import com.muesli.music.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserFacade {
    private final UserService userService;

    public UserInfo.Main registerUser(UserCommand command) {
        System.out.println("UserFacade :: registerUser");
        // 1. 이메일로 가입한 유저 검색
        var userinfo = userService.findUserInfo(command.getEmail());

        // 2. 가입한 유저가 있으면 중복오류 던지기
        if (userinfo.getId() != null) throw new BaseException(ErrorCode.COMMON_DUPLICATION_EMAIL);

        // 3. 가입 처리
        return userService.registerUser(command);
    }
}
