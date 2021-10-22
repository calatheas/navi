package com.calathea.navi.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum NormalResultCode implements CommonCode, ResultCode{
    // prefix : 도메인명
    COMMON_OK("20000", HttpStatus.OK.value(), "OK"),
    COMMON_CREATED("20100", HttpStatus.CREATED.value(), "Created"),
    COMMON_ACCEPTED("20200", HttpStatus.ACCEPTED.value(), "Accepted");

    private String code;
    private int status;
    private String defaultMessage;
}
