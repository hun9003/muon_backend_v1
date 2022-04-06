package com.muesli.music.common.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponse<T> {
    private Result result;
    private T data;
    private Map<String, String> message;
    private String errorCode;

    public static <T> CommonResponse<T> success(T data, Map<String, String> message) {
        return (CommonResponse<T>) CommonResponse.builder()
                .result(Result.SUCCESS)
                .data(data)
                .message(message)
                .build();
    }

    public static <T> CommonResponse<T> success(T data) {
        return success(data, null);
    }

    public static CommonResponse fail(String message, String errorCode) {
        var msg = new HashMap<String, String>();
        msg.put("error", message);
        return CommonResponse.builder()
                .result(Result.FAIL)
                .message(msg)
                .errorCode(errorCode)
                .build();
    }

    public static CommonResponse fail(ErrorCode errorCode) {
        var error = new HashMap<String, String>();
        error.put("error", errorCode.getErrorMsg());
        return CommonResponse.builder()
                .result(Result.FAIL)
                .message(error)
                .errorCode(errorCode.name())
                .build();
    }

    public enum Result {
        SUCCESS, FAIL
    }
}
