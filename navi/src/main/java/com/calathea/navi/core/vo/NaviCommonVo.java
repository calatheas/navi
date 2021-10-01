package com.calathea.navi.core.vo;

import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public abstract class NaviCommonVo {
    private String requestId;

    protected NaviCommonVo() {
        this.requestId = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
    }
}
