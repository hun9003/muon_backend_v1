package com.muesli.music.domain.user;

public interface UserReader {
    User getUser(Long userId);
    User getUser(String email);
}
