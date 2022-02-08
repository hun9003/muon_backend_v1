package com.muesli.music.domain.user;

import lombok.Getter;

@Getter
public class UserInfo {
    private final Long id;
    private final String username;
    private final String email;
    private final String password;
    private final String phone_number;
    private final Long alarm;
    private final Long alarm_midnight;
    private final String auth_type;

    public UserInfo(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.phone_number = user.getPhone_number();
        this.alarm = user.getAlarm();
        this.alarm_midnight = user.getAlarm_midnight();
        this.auth_type = user.getAuth_type();
    }
}
