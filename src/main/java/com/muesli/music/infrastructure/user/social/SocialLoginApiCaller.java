package com.muesli.music.infrastructure.user.social;

import com.muesli.music.domain.user.User;
import com.muesli.music.domain.user.UserCommand;
import com.muesli.music.domain.user.social.SocialMethod;

public interface SocialLoginApiCaller {

    boolean support(SocialMethod socialMethod);
    User getSocialUserInfo(UserCommand.SocialLoginRequest command);
}
