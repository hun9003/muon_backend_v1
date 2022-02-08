package com.muesli.music.domain.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserStore userStore;
    private final UserReader userReader;

    @Override
    public UserInfo registerUser(UserCommand command) {
        var initUser = command.toEntity();
        var user = userStore.store(initUser);
        return new UserInfo(user);
    }
}
