package com.muesli.music.domain.user;

import com.muesli.music.common.exception.BaseException;
import com.muesli.music.common.response.ErrorCode;
import com.muesli.music.common.util.HashGenerator;
import com.muesli.music.domain.user.token.UsertokenCommand;
import com.muesli.music.domain.user.token.UsertokenStore;
import com.muesli.music.infrastructure.user.social.GoogleLoginApiCaller;
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
    private final GoogleLoginApiCaller googleLoginApiCaller;

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
     * @param command
     * @return
     */
    @Override
    @Transactional
    public UserInfo.UsertokenInfo loginUser(UserCommand.LoginUserRequest command) {
        System.out.println("UserServiceImpl :: loginUser");
        var user = userReader.getUser(command.getEmail(), HashGenerator.hashPassword(command.getEmail(), command.getPassword()));
        if (user.getId() == null) throw new BaseException(ErrorCode.USER_LOGIN_FALE);
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

    /**
     * 닉내임이 중복 되는지 확인
     * @param username
     * @return
     */
    @Override
    public boolean isDuplicateUsername(String username) {
        System.out.println("UserServiceImpl :: isDuplicateUsername");
        var user = userReader.getUserByusername(username);
        return user.getId() != null;
    }

    /**
     * 비밀번호 변경
     * @param command
     */
    @Override
    @Transactional
    public void changePassword(UserCommand.ChangeUserPassword command, String email) {
        System.out.println("UserServiceImpl :: changePassword");
        var user = userReader.getUser(email);
        if (user.getPassword().equals(HashGenerator.hashPassword(email, command.getPassword()))) {
            user.setPassword(email, command.getNewPassword());
        } else {
            throw new BaseException(ErrorCode.USER_BAD_PASSWORD);
        }
    }

    @Override
    public User getSocialUserInfo(UserCommand.SocialLoginRequest socialLoginCommand) {
        System.out.println("UserServiceImpl :: getSocialUserInfo");
        switch (socialLoginCommand.getAuthType()) {
            case "google":
                return googleLoginApiCaller.getSocialUserInfo(socialLoginCommand);
            default: break;
        }
        return null;
    }

    @Override
    @Transactional
    public UserInfo.UsertokenInfo socialLoginUser(UserInfo.Main userInfo) {
        System.out.println("UserServiceImpl :: socialLoginUser");
        if (userInfo.getId() == null) throw new BaseException(ErrorCode.USER_LOGIN_FALE);
        var user = userReader.getUser(userInfo.getId());
        var initUsertoken = UsertokenCommand.makeToken(user);
        var usertoken = usertokenStore.store(initUsertoken);
        return new UserInfo.UsertokenInfo(usertoken, new UserInfo.Main(user));
    }


    @Override
    @Transactional
    public UserInfo.Main registerSocialUser(User initUser) {
        System.out.println("UserServiceImpl :: registerUser");
        var user = userStore.store(initUser);
        return new UserInfo.Main(user);
    }
}
