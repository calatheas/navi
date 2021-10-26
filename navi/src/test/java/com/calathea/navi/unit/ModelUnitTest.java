package com.calathea.navi.unit;

import com.calathea.navi.model.TruckVo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ModelUnitTest {
    @Test
    @DisplayName("상속 테스트")
    public void test() {
        // 추상클래스를 이용하여 공통 속성 강제하기
        TruckVo vo = new TruckVo("kyj");
        System.out.println(vo.getCarId());
    }
}