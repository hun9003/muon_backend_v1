package com.muesli.music.domain.user.token;

import com.muesli.music.common.exception.InvalidParamException;
import com.muesli.music.common.util.TokenGenerator;
import com.muesli.music.domain.AbstractEntity;
import com.muesli.music.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "user_id")
    private Long userId;

    private String token;
    private Long exp;

    @Column(name = "upload_at")
    private Timestamp uploadAt;

    @Builder
    public Usertoken(Long userId) {
        if (userId == null) throw new InvalidParamException("Usertoken.userId");

        this.userId = userId;
        this.token = TokenGenerator.randomCharacter(32);

        this.uploadAt = new Timestamp(System.currentTimeMillis());

        // 현재시간으로 부터 +3개월
        Timestamp time = new Timestamp(System.currentTimeMillis());
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        cal.add(Calendar.MONTH, 3);
        time.setTime(cal.getTime().getTime());

        this.exp = time.getTime();
    }

}
