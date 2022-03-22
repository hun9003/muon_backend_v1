package com.muesli.music.domain.user.token;

import com.muesli.music.common.util.TokenGenerator;
import com.muesli.music.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.sql.Timestamp;
import java.util.Calendar;


@Getter
@Builder
@ToString
public class UsertokenCommand {

    private final String token;
    private final Long exp;
    private final Timestamp uploadAt;
    private final User user;

    public Usertoken toEntity(User user) {
        System.out.println("UsertokenCommand :: toEntity");
        return Usertoken.builder()
                .user(user)
                .token(token)
                .exp(exp)
                .uploadAt(uploadAt)
                .build();
    }

    public static Usertoken makeToken(User user) {
        System.out.println("UsertokenCommand :: makeToken");
        String token = TokenGenerator.randomCharacter(40);
        // 현재시간으로 부터 +3개월
        Timestamp time = new Timestamp(System.currentTimeMillis());
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        cal.add(Calendar.MONTH, 3);
        time.setTime(cal.getTime().getTime());
        String setTime = time.getTime()+"";
        Timestamp uploadAt = new Timestamp(Long.parseLong(setTime));
        setTime = setTime.substring(0, setTime.length()-3);
        Long exp = Long.parseLong(setTime);
        return Usertoken.builder()
                .user(user)
                .token(token)
                .exp(exp)
                .uploadAt(uploadAt)
                .build();
    }

}
