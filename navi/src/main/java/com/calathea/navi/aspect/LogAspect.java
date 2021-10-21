package com.calathea.navi.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class LogAspect {
    @Before("execution(* com.calathea.navi..*(..))")
    public void beforeMethod(final JoinPoint joinPoint) {
        log.info("Test log!");
    }
}
