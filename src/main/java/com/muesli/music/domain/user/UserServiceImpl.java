package com.muesli.music.domain.user;

import com.muesli.music.domain.user.token.UsertokenCommand;
import com.muesli.music.domain.user.token.UsertokenStore;
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
    private final UsertokenStore usertokenStore;

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
    @Transactional
    public UserInfo.UsertokenInfo loginUser(String email, String password) {
        System.out.println("UserServiceImpl :: loginUser");
        var user = userReader.getUser(email, password);
        var initUsertoken = UsertokenCommand.makeToken(user);
        var usertoken = usertokenStore.store(initUsertoken);
        return new UserInfo.UsertokenInfo(usertoken, new UserInfo.Main(user));
    }

    @Override
    @Transactional
    public void changeConfirmed(String email) {
        System.out.println("UserServiceImpl :: changeConfirmed");
        var user = userReader.getUser(email);
        user.changeConfirmed();
    }

    @Override
    @Transactional
    public void registerUserUuid(String email) {
        System.out.println("UserServiceImpl :: registerUserUuid");
        var user = userReader.getUser(email);
        user.setUuid();
    }
}
