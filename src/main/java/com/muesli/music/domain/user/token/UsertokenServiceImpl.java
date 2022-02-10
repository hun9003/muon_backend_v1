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
    private UsertokenReader usertokenReader;
    private UserReader userReader;
    private UsertokenStore usertokenStore;

    @Override
    public UserInfo.UsertokenInfo findUsertokenInfo(String token) {
        System.out.println("UsertokenServiceImpl :: findUsertokenInfo");
        var usertoken = usertokenReader.getUsertoken(token);
        var user = userReader.getUser(usertoken.getUser().getId());
        return new UserInfo.UsertokenInfo(usertoken, user);
    }

    @Override
    public UserInfo.UsertokenInfo registerUsertoken(Usertoken usertoken) {
        System.out.println("UsertokenServiceImpl :: registerUsertoken");
        var userToken = usertokenStore.store(usertoken);
        var user = userReader.getUser(userToken.getId());
        return new UserInfo.UsertokenInfo(userToken, user);
    }
}
