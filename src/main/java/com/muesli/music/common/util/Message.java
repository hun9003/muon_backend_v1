package com.muesli.music.common.util;

import java.util.HashMap;
import java.util.Map;

public class Message {

    public static Map<String, String> makeResponseSuccessMessage(String dev, String user) {
        var message = new HashMap<String, String>();
        message.put("dev", dev);
        message.put("success", user);
        return message;
    }

    public static Map<String, String> makeResponseInfoMessage(String dev, String user) {
        var message = new HashMap<String, String>();
        message.put("dev", dev);
        message.put("info", user);
        return message;
    }

    public static Map<String, String> makeResponseMessage(String dev) {
        var message = new HashMap<String, String>();
        message.put("dev", dev);
        return message;
    }

    /**
     * 개발자용 메시지
     */
    public static class Developer {

        // 회원 관련
        public static final String SUCCESS_SIGNUP = "회원가입 완료";
        public static final String SUCCESS_SIGNIN = "로그인 완료";
        public static final String SUCCESS_EMAIL_VERIFICATION = "이메일 인증 완료";
        public static final String SUCCESS_CHANGE_PASSWORD = "비밀번호 변경 완료";
    }

    /**
     * 유저용 메시지
     */
    public static class User {

        // 회원 관련
        public static final String SUCCESS_SIGNUP = "회원가입을 축하드립니다.";
        public static final String SUCCESS_SIGNIN = "로그인에 성공했습니다.";
        public static final String SUCCESS_EMAIL_VERIFICATION = "이메일 인증에 성공했습니다.";
        public static final String SUCCESS_CHANGE_PASSWORD = "비밀번호 변경에 성공했습니다.";

        public static final String EMPTY_EMAIL = "이메일 작성은 필수 입니다.";
        public static final String BAD_EMAIL_PATTERN = "이메일 형식이 올바르지 않습니다.";
        public static final String EMPTY_USERNAME = "닉네임 작성은 필수 입니다.";
        public static final String EMPTY_PASSWORD = "비밀번호 작성은 필수 입니다.";
        public static final String BAD_PASSWORD_PATTERN = "비밀번호는 8 ~ 30 글자로 작성 하셔야 합니다.";
        public static final String BAD_PHONENUMBER_PATTERN = "휴대폰번호 형식이 올바르지 않습니다.";

    }
}
