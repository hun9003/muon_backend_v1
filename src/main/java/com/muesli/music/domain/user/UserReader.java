package com.muesli.music.domain.user;

public interface UserReader {
    User getUser(User user);
    User getUser(String email);
    User getUser(String email, String password);
}
