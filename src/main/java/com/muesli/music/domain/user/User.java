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

        // 비밀번호 암호화 작업
        String salt = HashGenerator.getSalt();
        String hashPassword;
        try {
            String digst = salt+password.trim()+salt+email.trim()+salt+HashGenerator.sha1(email+password)+salt;
            hashPassword = HashGenerator.sha1(salt+password+salt)
                    +HashGenerator.sha256(digst);
        } catch (NoSuchAlgorithmException e) {
            throw new InvalidParamException("fail password security");
        }


        this.username = username;
        this.email = email;
        this.password = hashPassword;
        this.phone_number = phone_number;
    }
}
