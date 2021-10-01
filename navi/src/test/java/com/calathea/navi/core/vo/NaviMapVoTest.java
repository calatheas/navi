package com.calathea.navi.core.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NaviMapVoTest {
    @Test
    @DisplayName("상속 테스트")
    public void test() {
        // 추상클래스를 이용하여 공통 속성 강제하기
        NaviMapVo vo = new NaviMapVo("kyj");
        System.out.println(vo.getRequestId());
    }
}