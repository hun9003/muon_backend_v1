package com.muesli.music.domain.user.token;

import com.muesli.music.common.exception.InvalidParamException;
import com.muesli.music.common.util.TokenGenerator;
import com.muesli.music.domain.AbstractEntity;
import com.muesli.music.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;

@Slf4j
@Getter
@Entity
@NoArgsConstructor
@Table(name = "usertoken")
public class Usertoken extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    private String token;
    private Long exp;

    @Column(name = "upload_at")
    private Timestamp uploadAt;

    @Builder
    public Usertoken(Long userId, String token, Long exp, Timestamp uploadAt) {
        if (userId == null) throw new InvalidParamException("empty username");
        if (StringUtils.isEmpty(token)) throw new InvalidParamException("empty token");
        if (exp == null) throw new InvalidParamException("empty exp");
        if (uploadAt == null) throw new InvalidParamException("empty uploadAt");
        this.userId = userId;
        this.token = token;
        this.exp = exp;
        this.uploadAt = uploadAt;
    }
}
