package com.muesli.music.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    COMMON_SYSTEM_ERROR("일시적인 오류가 발생했습니다. 잠시 후 다시 시도해주세요."), // 장애 상황
    COMMON_INVALID_PARAMETER("요청한 값이 올바르지 않습니다."),
    COMMON_ENTITY_NOT_FOUND("존재하지 않는 엔티티입니다."),
    COMMON_ILLEGAL_STATUS("잘못된 상태값입니다."),

    // 유저 관련 에러
    USER_DUPLICATION_EMAIL("이미 가입된 이메일 입니다."),
    USER_DUPLICATION_USERNAME("이미 가입된 닉네임 입니다."),
    USER_LOGIN_FALE("아이디 또는 비밀번호가 일치하지 않습니다."),
    USER_PERMISSION_FALE("권한이 없습니다"),
    USER_BAD_USERTOKEN("토큰이 유효하지 않습니다."),
    USER_BAD_PASSWORD("비밀번호가 일치하지 않습니다"),

    // EMPTY 관련
    EMPTY_SEARCH_KEYWORD("키워드가 없거나 1자 미만 입니다."),

    // 아이템 관련 에러
    ITEM_BAD_TYPE("타입이 올바르지 않습니다."),
    ITEM_EMPTY_RESULT("결과가 없습니다.");

    private final String errorMsg;

    public String getErrorMsg(Object... arg) {
        return String.format(errorMsg, arg);
    }
}
