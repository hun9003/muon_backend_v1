package com.muesli.music.domain.user;

import com.muesli.music.common.exception.InvalidParamException;
import com.muesli.music.domain.AbstractEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;

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
    private String phone_number;
    private Long alarm;
    private Long alarm_midnight;
    private String auth_type;

    @Builder
    public User(String username, String email, String password, String phone_number) {
        if (StringUtils.isEmpty(username)) throw new InvalidParamException("empty username");
        if (StringUtils.isEmpty(email)) throw new InvalidParamException("empty email");
        if (StringUtils.isEmpty(password)) throw new InvalidParamException("empty password");
        if (StringUtils.isEmpty(phone_number)) throw new InvalidParamException("empty phone_number");

        this.username = username;
        this.email = email;
        this.password = password;
        this.phone_number = phone_number;
    }
}
