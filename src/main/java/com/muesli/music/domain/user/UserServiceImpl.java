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

    /**
     * 유저 정보 저장
     * @param command
     * @return
     */
    @Override
    @Transactional
    public UserInfo.Main registerUser(UserCommand.RegisterUserRequest command) {
        System.out.println("UserServiceImpl :: registerUser");
        var initUser = command.toEntity();
        var user = userStore.store(initUser);
        return new UserInfo.Main(user);
    }

    /**
     * 유저 정보 조회
     * @param email
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public UserInfo.Main findUserInfo(String email) {
        System.out.println("UserServiceImpl :: findUserInfo");
        var user = userReader.getUser(email);
        return new UserInfo.Main(user);
    }

    /**
     * 유저 로그인
     * @param email
     * @param password
     * @return
     */
    @Override
    @Transactional
    public UserInfo.UsertokenInfo loginUser(String email, String password) {
        System.out.println("UserServiceImpl :: loginUser");
        var user = userReader.getUser(email, password);
        var initUsertoken = UsertokenCommand.makeToken(user);
        var usertoken = usertokenStore.store(initUsertoken);
        return new UserInfo.UsertokenInfo(usertoken, new UserInfo.Main(user));
    }

    /**
     * 유저 이메일 인증 처리
     * @param email
     */
    @Override
    @Transactional
    public void changeConfirmed(String email) {
        System.out.println("UserServiceImpl :: changeConfirmed");
        var user = userReader.getUser(email);
        user.changeConfirmed();
    }

    /**
     * 로그인시 UUID가 존재하지 않을 시 생성
     * @param email
     */
    @Override
    @Transactional
    public void registerUserUuid(String email) {
        System.out.println("UserServiceImpl :: registerUserUuid");
        var user = userReader.getUser(email);
        user.setUuid();
    }
}
