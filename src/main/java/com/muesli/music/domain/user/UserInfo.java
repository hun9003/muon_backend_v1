package com.muesli.music.domain.user;

import com.muesli.music.domain.user.token.Usertoken;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;

@Getter
public class UserInfo {
    @Getter
    @ToString
    public static class Main {
        private final Long id;
        private final String username;
        private final String email;
        private final String password;
        private final String phoneNumber;
        private final Long alarm;
        private final int confirmd;
//        private final Long alarmMidnight;
//        private final String authType;

        public Main(User user) {
            System.out.println("UserInfo.Main :: Main");
            this.id = user.getId();
            this.username = user.getUsername();
            this.email = user.getEmail();
            this.password = user.getPassword();
            this.phoneNumber = user.getPhoneNumber();
            this.alarm = user.getAlarm();
            this.confirmd = user.getConfirmd();
//            this.alarmMidnight = user.alarmMidnight();
//            this.auth_type = user.getAuth_type();
        }
    }

    @Getter
    @ToString
    public static class UsertokenInfo {
        private final String token;
        private final Long exp;
        private final User user;

        public UsertokenInfo(Usertoken usertoken, User user) {
            this.user = user;
            this.token = usertoken.getToken();
            this.exp = usertoken.getExp();
        }
    }
}
