package com.muesli.music.domain.user;

import com.muesli.music.common.exception.InvalidParamException;
import com.muesli.music.common.util.HashGenerator;
import com.muesli.music.common.util.UUIDGenerator;
import com.muesli.music.domain.AbstractEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "uuid")
    private String uuid;
    @Column(name = "username")
    private String username;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "phone_number")
    private String phoneNumber;
    private Long alarm;
    @Column(name = "alarm_midnight")
    private Long alarmMidnight;
    @Column(name = "auth_type")
    private String authType;
    @Column(name="confirmed")
    private int confirmed;
    @Column(name="confirmed_at")
    private Timestamp confirmedAt;
    @Column(name="gender")
    private String gender;
    @Column(name="birthday")
    private LocalDate birthday;


    @Builder
    public User(String username, String email, String password, String phoneNumber, String gender, LocalDate birthday) {
        if (StringUtils.isEmpty(username)) throw new InvalidParamException("empty username");
        if (StringUtils.isEmpty(email)) throw new InvalidParamException("empty email");
        if (StringUtils.isEmpty(password)) throw new InvalidParamException("empty password");

        this.username = username;
        this.email = email;
        this.password = HashGenerator.hashPassword(email, password);
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.birthday = birthday;
    }

    public void changeConfirmed() {
        this.confirmed = 1;
        this.confirmedAt = new Timestamp(System.currentTimeMillis());
    }

    public void setUuid() {
        this.uuid = UUIDGenerator.makeUUID();
    }

    public void setPassword(String email, String password) {
        this.password = HashGenerator.hashPassword(email, password);
    }
}
