package com.muesli.music.application.user;

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

    public UserInfo regisgerUser(UserCommand command) {
        var userInfo = userService.registerUser(command);

        // 가입 축하 메세지

        return userInfo;
    }
}
