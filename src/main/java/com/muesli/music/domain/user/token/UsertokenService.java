package com.muesli.music.domain.user.token;

import com.muesli.music.domain.user.UserInfo;

public interface UsertokenService {
    UserInfo.UsertokenInfo findUsertokenInfo(String token);

    UserInfo.UsertokenInfo registerUsertoken(UsertokenCommand command);

    void checkUsertoken(String token);
}
