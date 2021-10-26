package com.calathea.navi.core.service;

import com.calathea.navi.constants.CommonCodes;
import com.calathea.navi.model.CarRequestVo;
import com.calathea.navi.model.CarVo;
import com.calathea.navi.model.TruckVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Transactional(rollbackFor = Exception.class)
@Service
@RequiredArgsConstructor
public class CarService {
    public List<CarVo> getCars(CarRequestVo carRequestVo) {
        if (carRequestVo.getCarTypeCode() == CommonCodes.CarTypeCode.TRUCK) {
            return List.of(
                    new TruckVo("truck1"),
                    new TruckVo("truck2")
            );
        } else {
            throw new NotImplementedException();
        }
    }
}
