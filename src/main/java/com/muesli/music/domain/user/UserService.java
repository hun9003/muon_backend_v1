package com.muesli.music.domain.user;

public interface UserService {
    UserInfo.Main registerUser(UserCommand command);

    UserInfo.Main findUserInfo(String email);

    UserInfo.Main loginUser(String email, String password);

    void changeConfirmed(String email);

    void registerUserUuid(String email);
}
