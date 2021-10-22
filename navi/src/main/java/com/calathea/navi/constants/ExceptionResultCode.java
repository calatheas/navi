package com.calathea.navi.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ExceptionResultCode implements CommonCode, ResultCode{
    // prefix : 도메인명
    COMMON_BAD_REQUEST("40000", HttpStatus.BAD_REQUEST.value(), "Bad Request"),
    COMMON_FORBIDDEN("40300", HttpStatus.FORBIDDEN.value(), "Forbidden"),
    COMMON_INTERNAL_SERVER_ERROR("50000", HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal Server Error");

    private String code;
    private int status;
    private String defaultMessage;
}
