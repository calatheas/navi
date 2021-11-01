package com.calathea.navi.core.controller;

import com.calathea.navi.constants.CommonCodes;
import com.calathea.navi.controller.ApiController;
import com.calathea.navi.core.service.CarService;
import com.calathea.navi.model.CarVo;
import com.calathea.navi.model.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@ApiController
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;

    @GetMapping("/cars")
    public CommonResponse<List<CarVo>> getCars(@RequestParam CommonCodes.CarTypeCode carTypeCode) {
        return CommonResponse.onSuccess(carService.getCars(carTypeCode));
    }
}
