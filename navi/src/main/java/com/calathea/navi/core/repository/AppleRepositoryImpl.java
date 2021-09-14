package com.calathea.navi.core.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

@Slf4j
@Repository
public class AppleRepositoryImpl {
    public Long growApple() {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.info("growApple");
        return new BigDecimal(Math.random()*10).longValue();
    }
}
