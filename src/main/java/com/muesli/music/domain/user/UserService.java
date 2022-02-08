package com.muesli.music.domain.user;

public interface UserService {
    UserInfo registerUser(UserCommand command);
}
