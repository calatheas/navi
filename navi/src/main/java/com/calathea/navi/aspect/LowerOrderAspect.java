package com.calathea.navi.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Aspect
@Order(Ordered.LOWEST_PRECEDENCE)
public class LowerOrderAspect {
    @Around("execution(* com.calathea.navi.*.service..*(..))")
    public Object log(final ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("Lower order aspect log!");
        Object results = joinPoint.proceed();
        log.info("Lower order aspect log!");
        return results;
    }
}
