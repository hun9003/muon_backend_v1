package com.muesli.music.domain.user.token;

import com.muesli.music.common.exception.BaseException;
import com.muesli.music.common.response.ErrorCode;
import com.muesli.music.domain.user.User;
import com.muesli.music.domain.user.UserInfo;
import com.muesli.music.domain.user.UserReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsertokenServiceImpl implements UsertokenService {
    private final UsertokenReader usertokenReader;
    private final UserReader userReader;
    private final UsertokenStore usertokenStore;

    @Override
    public UserInfo.UsertokenInfo findUsertokenInfo(String token) {
        System.out.println("UsertokenServiceImpl :: findUsertokenInfo");
        var usertoken = usertokenReader.getUsertoken(token);
        UserInfo.Main userInfo;
        try {
            if (usertoken.getUploadAt().before(new Timestamp(System.currentTimeMillis()))) throw new BaseException(ErrorCode.COMMON_BAD_USERTOKEN);
            userInfo = new UserInfo.Main(userReader.getUser(usertoken.getUser()));
        } catch (Exception e){
            userInfo = new UserInfo.Main(new User());
        }
        return new UserInfo.UsertokenInfo(usertoken, userInfo);
    }

    @Override
    public UserInfo.UsertokenInfo registerUsertoken(UsertokenCommand command) {
        System.out.println("UsertokenServiceImpl :: registerUsertoken");
        var initUsertoken = command.toEntity(command.getUser());
        var userToken = usertokenStore.store(initUsertoken);
        var user = new UserInfo.Main(userReader.getUser(command.getUser()));
        return new UserInfo.UsertokenInfo(userToken, user);
    }
}
