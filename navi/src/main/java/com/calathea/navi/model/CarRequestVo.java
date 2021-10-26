package com.calathea.navi.model;

import com.calathea.navi.constants.CommonCodes;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class CarRequestVo {
    @NotNull
    private CommonCodes.CarTypeCode carTypeCode;
}
