package com.calathea.navi.model;

import com.calathea.navi.constants.ExceptionResultCode;
import com.calathea.navi.constants.NormalResultCode;
import com.calathea.navi.constants.ResultCode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
public class CommonResponse<T> {
    private ResultCode resultCode;
    private String message;
    private T data;

    public static <T> CommonResponse<T> onSuccess() {
        return CommonResponse.<T>builder()
                .resultCode(NormalResultCode.COMMON_OK)
                .message(NormalResultCode.COMMON_OK.getDefaultMessage())
                .build();
    }

    public static <T> CommonResponse<T> onSuccess(final T data) {
        return CommonResponse.<T>builder()
                .resultCode(NormalResultCode.COMMON_OK)
                .message(NormalResultCode.COMMON_OK.getDefaultMessage())
                .data(data)
                .build();
    }

    public static <T> CommonResponse<T> onBadRequest(final T data) {
        return CommonResponse.<T>builder()
                .resultCode(ExceptionResultCode.COMMON_BAD_REQUEST)
                .message(ExceptionResultCode.COMMON_BAD_REQUEST.getDefaultMessage())
                .data(data)
                .build();
    }

    public static <T> CommonResponse<T> onFailure(final String errMessage) {
        return CommonResponse.<T>builder()
                .resultCode(ExceptionResultCode.COMMON_INTERNAL_SERVER_ERROR)
                .message(errMessage)
                .build();
    }
}
