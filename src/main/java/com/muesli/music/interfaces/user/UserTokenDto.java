package com.muesli.music.interfaces.user;

import com.muesli.music.common.util.TokenGenerator;
import com.muesli.music.domain.user.token.UsertokenCommand;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.sql.Timestamp;
import java.util.Calendar;

public class UserTokenDto {

    @Getter
    @ToString
    public static class RegisterUserToken {

        Long userId;
        String token;
        Long exp;
        Timestamp uploadAt;

        public RegisterUserToken(Long userId) {
            this.userId = userId;
            this.token = TokenGenerator.randomCharacter(40);
            this.uploadAt = new Timestamp(System.currentTimeMillis());

            // 현재시간으로 부터 +3개월
            Timestamp time = new Timestamp(System.currentTimeMillis());
            Calendar cal = Calendar.getInstance();
            cal.setTime(time);
            cal.add(Calendar.MONTH, 3);
            time.setTime(cal.getTime().getTime());
            String setTime = time.getTime()+"";
            setTime = setTime.substring(0, setTime.length()-3);
            this.exp = Long.parseLong(setTime);
        }

        public UsertokenCommand toCommand() {
            return UsertokenCommand.builder()
                    .userId(userId)
                    .token(token)
                    .exp(exp)
                    .uploadAt(uploadAt)
                    .build();
        }
    }
}
