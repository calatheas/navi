package com.calathea.navi.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class CommonCodes {
    @AllArgsConstructor
    @Getter
    public enum CarTypeCode implements CommonCode {
        TRUCK("TRUCK");

        private String code;
    }
}
