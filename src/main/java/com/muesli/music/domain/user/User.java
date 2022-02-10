package com.muesli.music.domain.user;

import com.muesli.music.common.exception.InvalidParamException;
import com.muesli.music.common.util.HashGenerator;
import com.muesli.music.domain.AbstractEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.security.NoSuchAlgorithmException;
import java.time.ZonedDateTime;

@Slf4j
@Getter
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String password;
    @Column(name = "phone_number")
    private String phoneNumber;
    private Long alarm;
//    @Column(name = "alarm_midnight")
//    private Long alarmMidnight;
//    @Column(name = "auth_type")
//    private String authType;
    private int confirmd;
    @Column(name="confirmed_at")
    private ZonedDateTime confirmedAt;

    @Builder
    public User(String username, String email, String password, String phoneNumber) {
        if (StringUtils.isEmpty(username)) throw new InvalidParamException("empty username");
        if (StringUtils.isEmpty(email)) throw new InvalidParamException("empty email");
        if (StringUtils.isEmpty(password)) throw new InvalidParamException("empty password");
        if (StringUtils.isEmpty(phoneNumber)) throw new InvalidParamException("empty phone_number");



        this.username = username;
        this.email = email;
        this.password = HashGenerator.hashPassword(email, password);
        this.phoneNumber = phoneNumber;
    }

    public void changeConfirmd() {
        this.confirmd = 1;
    }
}
