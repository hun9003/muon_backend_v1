package com.muesli.music.domain.user.token;

import com.muesli.music.domain.user.UserInfo;
import com.muesli.music.domain.user.UserReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsertokenServiceImpl implements UsertokenService{
    private final UsertokenReader usertokenReader;
    private final UserReader userReader;
    private final UsertokenStore usertokenStore;

    @Override
    public UserInfo.UsertokenInfo findUsertokenInfo(String token) {
        System.out.println("UsertokenServiceImpl :: findUsertokenInfo");
        var usertoken = usertokenReader.getUsertoken(token);
        var user = new UserInfo.Main(userReader.getUser(usertoken.getUserId()));
        return new UserInfo.UsertokenInfo(usertoken, user);
    }

    @Override
    public UserInfo.UsertokenInfo registerUsertoken(UsertokenCommand command) {
        System.out.println("UsertokenServiceImpl :: registerUsertoken");
        var initUsertoken = command.toEntity(command.getUserId());
        var userToken = usertokenStore.store(initUsertoken);
        var user = new UserInfo.Main(userReader.getUser(command.getUserId()));
        return new UserInfo.UsertokenInfo(userToken, user);
    }
}
