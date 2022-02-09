package com.muesli.music.domain.user;

public interface UserService {
    UserInfo.Main registerUser(UserCommand command);

    UserInfo.Main findUserInfo(String email);
}
