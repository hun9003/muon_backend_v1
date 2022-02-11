package com.muesli.music.domain.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserStore userStore;
    private final UserReader userReader;

    @Override
    @Transactional
    public UserInfo.Main registerUser(UserCommand command) {
        System.out.println("UserServiceImpl :: registerUser");
        var initUser = command.toEntity();
        var user = userStore.store(initUser);
        return new UserInfo.Main(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserInfo.Main findUserInfo(String email) {
        System.out.println("UserServiceImpl :: findUserInfo");
        var user = userReader.getUser(email);
        return new UserInfo.Main(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserInfo.Main loginUser(String email, String password) {
        System.out.println("UserServiceImpl :: loginUser");
        var user = userReader.getUser(email, password);
        return new UserInfo.Main(user);
    }

    @Override
    @Transactional
    public void changeConfirmed(String email) {
        System.out.println("UserServiceImpl :: changeConfirmed");
        var user = userReader.getUser(email);
        user.changeConfirmed();
    }
}
