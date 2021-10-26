package com.calathea.navi.core.controller;

import com.calathea.navi.controller.ApiController;
import com.calathea.navi.core.service.CarService;
import com.calathea.navi.model.CarRequestVo;
import com.calathea.navi.model.CarVo;
import com.calathea.navi.model.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

import javax.validation.Valid;
import java.util.List;

@ApiController
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;

    @GetMapping("/cars")
    public CommonResponse<List<CarVo>> getCars(@Valid CarRequestVo carRequestVo) {
        return CommonResponse.onSuccess(carService.getCars(carRequestVo));
    }
}
